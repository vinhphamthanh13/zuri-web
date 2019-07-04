import React, { PureComponent } from 'react';
import { bool, string, number, func, oneOfType, node } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import noop from 'lodash/noop';
import classnames from 'classnames';
import debounce from 'lodash/debounce';
import Input from 'components/common/Input';
import find from 'lodash/find';
import isFunction from 'lodash/isFunction';
import FontIcon from 'components/common/FontIcon';

import { NO_MATCH, NO_DATA } from './constant';
import s from './Select.css';

class Select extends PureComponent {
  static propTypes = {
    // Event handler props
    onChange: func,
    filterMethod: func,
    // Props setting
    value: oneOfType([number, string]),
    name: string,
    className: string,
    placeholder: string,
    errorMsg: string,
    disabled: bool,
    multiple: bool,
    required: bool,
    filterable: bool,
    touched: bool,
    children: node,
    classNameWrapperOptions: string,
    // Props text
    textNoData: string,
    textNoMatch: string,
  };

  static defaultProps = {
    // Event props
    onChange: noop,
    filterMethod: null,
    // Props setting
    value: undefined,
    name: undefined,
    className: '',
    placeholder: '',
    errorMsg: '',
    disabled: false,
    multiple: false,
    required: false,
    filterable: false,
    touched: false,
    children: null,
    classNameWrapperOptions: '',
    textNoData: NO_DATA,
    textNoMatch: NO_MATCH,
  };

  constructor(props) {
    super(props);

    const visibleLabel = this.getCurrentLabel();

    this.state = {
      visible: false,
      query: '',
      visibleLabel,
    };

    this.handleQueryChange = debounce(this.handleQueryChange, 100);
    this.root = React.createRef();
    this.input = React.createRef();
  }

  componentDidMount() {
    document.addEventListener('mousedown', this.handleClickOutSide);
  }

  componentWillUnmount() {
    document.removeEventListener('mousedown', this.handleClickOutSide);
  }

  getCurrentLabel = () => {
    const { value, children } = this.props;
    let visibleLabel = '';

    if (value) {
      const optionSelected = find(
        children,
        option => option.props.value === value,
      );

      visibleLabel = optionSelected.props.label;
    }

    return visibleLabel;
  };

  /**
   * handle if user click outside component
   * blur it and reset visibleLabel, query, countOptionvisible
   * @param  {SyntheticEvent} e
   */
  handleClickOutSide = e => {
    if (!this.root.current.contains(e.target)) {
      const { children } = this.props;
      const visibleLabel = this.getCurrentLabel();

      this.setState(() => ({
        visible: false,
        visibleLabel,
        query: '',
        countOptionvisible: children.length,
      }));
    }
  };

  handleOptionClick = option => {
    const { name } = this.props;

    this.props.onChange(name, option);
    this.setState(() => ({
      visibleLabel: option.label,
      visible: false,
      query: '',
    }));
  };

  /**
   * @function handleInputClick
   * This func will handle every user click to select's input
   * @return {type} {description}
   */
  handleInputClick = e => {
    e.preventDefault();
    const { disabled, filterable } = this.props;
    const { visible } = this.state;

    if (disabled) return;

    // If filterable is disable
    if (!filterable) {
      this.toggleOptionsWrapper();
      return;
    }

    // If options not open
    if (!visible) {
      e.target.select();
      this.setState({
        visible: true,
      });
      return;
    }

    // If options open
    if (e.target === document.activeElement) {
      e.target.blur();
      this.setState({
        visible: false,
      });
    } else {
      e.target.select();
    }
  };

  toggleOptionsWrapper = () => {
    this.setState(preState => ({
      visible: !preState.visible,
    }));
  };

  isMatchWithQuery = (option, query) => {
    const { label } = option;
    const { filterMethod } = this.props;

    if (!query) return true;
    if (isFunction(filterMethod)) {
      return filterMethod(option, query);
    }

    const normalLabel = label
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .toLowerCase();
    const normarQuery = query
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .toLowerCase();
    return `${normalLabel}`.includes(normarQuery);
  };

  handleInputvisibleChange = e => {
    const { value } = e.target;

    this.setState({
      visibleLabel: value,
    });

    this.handleQueryChange();
  };

  /**
   * handleQueryChange this function change state query
   * @param  {Event} e Target event for input change
   */
  handleQueryChange = () => {
    const { visibleLabel } = this.state;

    this.setState({
      query: visibleLabel,
    });
  };

  /**
   * @function renderListOption
   * render list option or message
   */
  renderListOption = () => {
    const { query } = this.state;
    const { children } = this.props;
    let options = [];

    if (children.length) {
      options = React.Children.map(children, optionItem => {
        let classNameOption = '';
        const isShow = this.isMatchWithQuery(optionItem.props, query);
        if (!isShow) {
          classNameOption = s.optionDisplayNone;
        }

        return React.cloneElement(optionItem, {
          changeValue: this.handleOptionClick,
          className: classNameOption,
        });
      });
    }

    return options;
  };

  /**
   * @function renderAlertMessage
   * This render NOT to render error message
   * This message FOR loading, no match query, no current options
   * @return {Node}
   */
  renderAlertMessage = () => {
    const { query } = this.state;
    const { children, textNoData, textNoMatch } = this.props;
    let countOptionvisible = children.length;

    if (children.length) {
      React.Children.map(children, optionItem => {
        const isShow = this.isMatchWithQuery(optionItem.props, query);
        if (!isShow) countOptionvisible -= 1;
      });
    }

    if (!children.length) {
      return (
        <p key="no-option" className={s.optionMessage}>
          {textNoData}
        </p>
      );
    } else if (!countOptionvisible) {
      return (
        <p key="no-match" className={s.optionMessage}>
          {textNoMatch}
        </p>
      );
    }

    return null;
  };

  render() {
    const {
      placeholder,
      multiple,
      filterable,
      name,
      className,
      classNameWrapperOptions,
      required,
      errorMsg,
      touched,
    } = this.props;
    const { visible, visibleLabel } = this.state;

    return (
      <div className={classnames(s.select, className)} ref={this.root}>
        <div className={s.selectInputWrapper}>
          <Input
            value={visibleLabel}
            className={classnames(s.selectInput, className)}
            readOnly={!filterable || multiple}
            onMouseDown={this.handleInputClick}
            onChange={this.handleInputvisibleChange}
            required={required}
            name={name}
            id={name}
            ref={this.input}
            label={placeholder}
            errorMsg={errorMsg}
            touched={touched}
            autoComplete={false}
          />
          <FontIcon className={s.selectIconDropdown}>
            <span>keyboard_arrow_down</span>
          </FontIcon>
        </div>
        <div
          className={classnames(
            classNameWrapperOptions,
            s.optionsWrapper,
            !visible && s.optionsWrapperHidden,
          )}
        >
          <ul className={s.options}>{this.renderListOption()}</ul>
          {this.renderAlertMessage()}
        </div>
      </div>
    );
  }
}

export default withStyles(s)(Select);

import React, { PureComponent } from 'react';
import { func, string, number, object, arrayOf, oneOfType } from 'prop-types';
import classNames from 'classnames';
import { find, noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Dropdown.css';

class Dropdown extends PureComponent {
  static propTypes = {
    onChange: func,
    options: arrayOf(object),
    value: oneOfType([string, number]),
    placeholder: string,
    name: string,
    className: string,
    tabIndex: number,
  };

  static defaultProps = {
    onChange: noop,
    options: [],
    placeholder: '...',
    name: '',
    value: '',
    className: '',
    tabIndex: -1,
  };

  constructor(props) {
    super(props);
    this.state = {
      showContent: false,
    };
  }

  onOptionClick = option => {
    this.props.onChange && this.props.onChange(this.props.name, option);
    this.setState({ showContent: false });
  };

  onKeyUp = () => {};

  getActiveOption = () => {
    const { options, value } = this.props;
    return find(options, { value });
  };

  toggleDropdown = e => {
    e.stopPropagation();
    this.setState({ showContent: !this.state.showContent });
  };

  resetDropdown = e => {
    e.stopPropagation();
    this.setState({ showContent: false });
  };

  renderOption = option => (
    <span
      key={option.value}
      onClick={() => this.onOptionClick(option)}
      role="button"
      tabIndex={-1}
      onKeyUp={this.onKeyUp}
    >
      {option.label}
    </span>
  );

  render() {
    const { options, placeholder, className, tabIndex } = this.props;
    const { showContent } = this.state;
    const contentClasses = classNames(s.content, {
      [s.show]: showContent,
    });
    const activeOption = this.getActiveOption();
    const displayText = activeOption ? activeOption.label : placeholder;

    return (
      <div className={`${s.dropdown} ${className}`}>
        <div
          onClick={this.toggleDropdown}
          className={`${s.dropbtn} ${s.withEffect}`}
          data-dropdown="y"
          tabIndex={tabIndex || -1}
          role="button"
          onKeyUp={this.onKeyUp}
        >
          {displayText}
          <span className="material-icons">keyboard_arrow_down</span>
        </div>
        <span className={s.focusBorder} />
        <div className={contentClasses} data-dropdown="y">
          {options.map(this.renderOption)}
          {options.length === 0 && <span>Không có kết quả nào</span>}
        </div>
        {showContent && (
          <div
            className={s.overlay}
            role="button"
            tabIndex={-1}
            onKeyUp={this.onKeyUp}
            onClick={this.resetDropdown}
          />
        )}
      </div>
    );
  }
}

export default withStyles(s)(Dropdown);

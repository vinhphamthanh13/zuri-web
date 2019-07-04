import React from 'react';
import { bool, string, number, object, oneOfType, func } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import classnames from 'classnames';
import noop from 'lodash/noop';

import s from './Option.css';

class Option extends React.Component {
  static propTypes = {
    value: oneOfType([number, string, object]).isRequired,
    label: oneOfType([number, string]).isRequired,
    disabled: bool,
    changeValue: func,
    className: string,
  };

  static defaultProps = {
    disabled: false,
    changeValue: noop,
    className: '',
  };

  /**
   * Just make sure it not
   * re-render again
   */
  shouldComponentUpdate(nextProps) {
    const { label, value, className } = this.props;
    const {
      label: nextLabel,
      value: nextValue,
      className: nextClassName,
    } = nextProps;

    if (
      className !== nextClassName ||
      label !== nextLabel ||
      value !== nextValue
    ) {
      return true;
    }

    return false;
  }

  handleClick = () => {
    const { value, label, changeValue, disabled } = this.props;

    if (!disabled) {
      changeValue({ value, label });
    }
  };

  render() {
    const { label, disabled, className } = this.props;

    return (
      <li
        className={classnames(s.option, disabled && s.optionDisable, className)}
      >
        <a className={s.optionLink} onMouseDown={this.handleClick}>
          {label}
        </a>
      </li>
    );
  }
}

export default withStyles(s)(Option);

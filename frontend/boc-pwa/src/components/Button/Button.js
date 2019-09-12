import React, { Component } from 'react';
import { string, func, bool, node } from 'prop-types';
import { noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Button.css';

class Button extends Component {
  static propTypes = {
    label: string,
    className: string,
    disabled: bool,
    onClick: func,
    children: node,
    type: string,
    icon: bool,
    variant: string,
  };

  static defaultProps = {
    label: '',
    className: '',
    disabled: false,
    onClick: noop,
    children: null,
    type: '',
    icon: false,
    variant: 'main',
  };

  render() {
    const {
      label,
      className,
      onClick,
      disabled,
      type,
      icon,
      variant,
    } = this.props;

    let buttonStyle = /text/i.test(variant)
      ? `${s.button} ${s.buttonText} ${className}`
      : `${s.button} ${className}`;
    buttonStyle = disabled ? `${buttonStyle} ${s.buttonDisabled}` : buttonStyle;
    buttonStyle = icon ? `${buttonStyle} ${s.icon}` : buttonStyle;
    const props = {
      className: buttonStyle,
      onClick: disabled ? noop : onClick,
      type: disabled ? 'button' : type,
    };

    return (
      <button {...props}>
        {this.props.children}
        {!icon && <span className={s.label}>{label}</span>}
      </button>
    );
  }
}

export default withStyles(s)(Button);

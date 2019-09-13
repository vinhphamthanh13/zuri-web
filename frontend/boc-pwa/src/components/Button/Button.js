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
    gutter: bool,
    small: bool,
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
    gutter: false,
    small: false,
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
      gutter,
      small,
    } = this.props;

    let buttonStyle = `${s.button} ${className}`;
    if (/text/i.test(variant)) {
      buttonStyle = `${s.button} ${s.buttonText} ${className}`;
    } else if (/outlined/i.test(variant)) {
      buttonStyle = `${s.button} ${s.buttonOutlined} ${className}`;
    }
    buttonStyle = disabled ? `${buttonStyle} ${s.buttonDisabled}` : buttonStyle;
    buttonStyle = icon ? `${buttonStyle} ${s.icon}` : buttonStyle;
    buttonStyle = gutter ? `${buttonStyle} ${s.gutter}` : buttonStyle;

    const labelStyle = small ? `${s.label} ${s.labelSmall}` : s.label;
    const props = {
      className: buttonStyle,
      onClick: disabled ? noop : onClick,
      type: disabled ? 'button' : type,
    };

    return (
      <button {...props}>
        {this.props.children}
        {!icon && <span className={labelStyle}>{label}</span>}
      </button>
    );
  }
}

export default withStyles(s)(Button);

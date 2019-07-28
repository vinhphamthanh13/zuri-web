import React, { Component } from 'react';
import { string, func, bool, node } from 'prop-types';
import { noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Button.css';

class Button extends Component {
  static propTypes = {
    label: string.isRequired,
    className: string,
    disabled: bool,
    onClick: func,
    children: node,
    type: string,
  };

  static defaultProps = {
    className: '',
    disabled: false,
    onClick: noop,
    children: null,
    type: '',
  };

  render() {
    const { label, className, onClick, disabled, type } = this.props;
    const props = {
      className: disabled
        ? `${s.button} ${className} ${s.buttonDisabled}`
        : `${s.button} ${className}`,
      onClick: disabled ? noop : onClick,
      type: disabled ? 'button' : type,
    };

    return (
      <button {...props}>
        {this.props.children}
        <span className={s.label}>{label}</span>
      </button>
    );
  }
}

export default withStyles(s)(Button);

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
  };

  static defaultProps = {
    className: '',
    disabled: false,
    onClick: noop,
    children: null,
  };

  render() {
    const { label, className, onClick, disabled } = this.props;
    const props = {
      className: disabled
        ? `${s.button} ${className} ${s.disabled}`
        : `${s.button} ${className}`,
      onClick: disabled ? noop : onClick,
    };

    return (
      <div {...props}>
        {this.props.children}
        <span className={s.label}>{label}</span>
      </div>
    );
  }
}

export default withStyles(s)(Button);

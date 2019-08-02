import React, { Component } from 'react';
import { string, oneOfType, number, func, bool, objectOf } from 'prop-types';
import { noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import s from './Input.css';

class Input extends Component {
  static propTypes = {
    type: string,
    name: string.isRequired,
    value: oneOfType([string, number]).isRequired,
    onChange: func,
    className: string,
    disabled: bool,
    errors: objectOf(string),
    placeholder: string,
    label: string,
  };

  static defaultProps = {
    type: 'text',
    onChange: noop,
    disabled: false,
    errors: {},
    className: '',
    placeholder: '',
    label: '',
  };

  render() {
    const {
      type,
      name,
      value,
      onChange,
      className,
      disabled,
      errors,
      placeholder,
      label,
    } = this.props;
    const wrapperStyle = label
      ? s.inputWrapper
      : `${s.inputWrapper} ${s.inputWrapperGutter}`;

    return (
      <div className={wrapperStyle}>
        <span className={s.label}>{label}</span>
        <input
          id={name}
          placeholder={placeholder}
          type={type}
          name={name}
          value={value}
          onChange={onChange}
          className={className}
          disabled={disabled}
        />
        {errors[name] && <div className={s.errorMessage}>{errors[name]}</div>}
      </div>
    );
  }
}

export default withStyles(s)(Input);

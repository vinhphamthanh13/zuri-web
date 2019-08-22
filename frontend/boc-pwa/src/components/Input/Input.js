import React, { Component } from 'react';
import { string, oneOfType, number, func, bool, objectOf } from 'prop-types';
import { noop } from 'lodash';
import { gray } from 'constants/colors';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { ArrowForward, Search } from 'constants/svg';

import s from './Input.css';

class Input extends Component {
  static propTypes = {
    type: string,
    name: string.isRequired,
    value: oneOfType([string, number]),
    onChange: func,
    className: string,
    disabled: bool,
    errors: objectOf(string),
    placeholder: string,
    label: string,
    gutter: bool,
    dropDown: func,
    search: bool,
  };

  static defaultProps = {
    type: 'text',
    value: '',
    onChange: noop,
    disabled: false,
    errors: {},
    className: '',
    placeholder: '',
    label: '',
    gutter: false,
    dropDown: null,
    search: false,
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
      gutter,
      dropDown,
      search,
    } = this.props;
    const wrapperStyle = label
      ? s.inputWrapper
      : `${s.inputWrapper} ${s.inputWrapperGutter}`;
    const gutterStyle = gutter ? s.inputWrapperGutter : '';
    const dropDownButton = !dropDown ? null : <ArrowForward />;

    return (
      <div className={`${wrapperStyle} ${gutterStyle}`}>
        <span className={s.label}>{label}</span>
        <div className={s.search}>
          {search && <Search size={24} hexColor={gray} />}
        </div>
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
        {dropDownButton}
        {errors[name] && <div className={s.errorMessage}>{errors[name]}</div>}
      </div>
    );
  }
}

export default withStyles(s)(Input);

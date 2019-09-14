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
    value: oneOfType([string, number, bool]),
    onChange: func,
    onTouch: func,
    className: string,
    disabled: bool,
    errors: objectOf(string),
    placeholder: string,
    label: string,
    gutter: bool,
    dropDown: func,
    search: bool,
    touched: objectOf(bool),
  };

  static defaultProps = {
    type: 'text',
    value: '',
    onChange: noop,
    onTouch: noop,
    disabled: false,
    errors: {},
    className: '',
    placeholder: '',
    label: '',
    gutter: false,
    dropDown: null,
    search: false,
    touched: {},
  };

  handleOnchange = event => {
    const { onChange, onTouch, name } = this.props;
    onChange(event);
    onTouch(name, true);
  };

  render() {
    const {
      type,
      name,
      value,
      className,
      disabled,
      errors,
      placeholder,
      label,
      gutter,
      dropDown,
      search,
      touched,
    } = this.props;
    const wrapperStyle = label
      ? s.inputWrapper
      : `${s.inputWrapper} ${s.inputWrapperGutter}`;
    const gutterStyle = gutter ? s.inputWrapperGutter : '';

    const dropDownButton = !dropDown ? null : <ArrowForward />;

    return type === 'checkbox' ? (
      <div className={`${s.checkBoxGroup} ${className}`}>
        <input
          id={name}
          type={type}
          name={name}
          value={value}
          className={s.checkBox}
          onChange={this.handleOnchange}
        />
        <div className={s.checkBoxLabel}>{label}</div>
      </div>
    ) : (
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
          onChange={this.handleOnchange}
          className={className}
          disabled={disabled}
        />
        {dropDownButton}
        {errors[name] &&
          touched[name] && <div className={s.errorMessage}>{errors[name]}</div>}
      </div>
    );
  }
}

export default withStyles(s)(Input);

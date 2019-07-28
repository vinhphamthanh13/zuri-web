import React, { Component } from 'react';
import { string, oneOfType, number, func, bool, objectOf } from 'prop-types';
import uuidv1 from 'uuid/v1';
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
  };

  static defaultProps = {
    type: 'text',
    onChange: noop,
    disabled: false,
    errors: {},
    className: '',
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
    } = this.props;

    return (
      <div className={s.inputWrapper}>
        <input
          id={uuidv1()}
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

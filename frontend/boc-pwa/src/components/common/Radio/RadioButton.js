/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

/* eslint-disable jsx-a11y/label-has-for, css-modules/no-unused-class */

import React from 'react';
import PropTypes from 'prop-types';
import ClassNames from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Radio from './Radio';
import s from './style.css';

class RadioButton extends React.Component {
  static propTypes = {
    checked: PropTypes.bool,
    className: PropTypes.string,
    disabled: PropTypes.bool,
    label: PropTypes.string,
    name: PropTypes.string,
    onBlur: PropTypes.func,
    onChange: PropTypes.func,
    onFocus: PropTypes.func,
    value: PropTypes.string,
  };

  static defaultProps = {
    checked: false,
    className: '',
    disabled: false,
    label: '',
    name: '',
    onBlur: () => {},
    onChange: () => {},
    onFocus: () => {},
    value: '',
  };

  handleClick = event => {
    const { checked, disabled, onChange, value } = this.props;
    if (event.pageX !== 0 && event.pageY !== 0) {
      this.blur();
    }
    if (!disabled && !checked && onChange) {
      onChange(value);
    }
  };

  blur() {
    this.inputNode.blur();
  }

  focus() {
    this.inputNode.focus();
  }

  nodeRef = node => {
    this.inputNode = node;
  };

  render() {
    const className = ClassNames(
      this.props.disabled ? s.disabled : s.field,
      this.props.className,
    );
    const { onChange, children, ...others } = this.props;

    return (
      <label className={className}>
        <input
          {...others}
          className={s.input}
          onClick={this.handleClick}
          readOnly
          ref={this.nodeRef}
          type="radio"
        />
        <Radio checked={this.props.checked} disabled={this.props.disabled} />
        {this.props.label && (
          <span className={s.text}>
            {this.props.label}
            {children}
          </span>
        )}
      </label>
    );
  }
}

export default withStyles(s)(RadioButton);

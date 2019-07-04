import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { toString, noop } from 'lodash';
import {
  setCaretPosition,
  formatInput,
  getCursorPosition,
} from './NumberUtils';
import Input from '../Input';

/* eslint-disable react/no-unused-prop-types, react/no-unused-state */

class NumberInput extends Component {
  static propTypes = {
    thousandSeparator: PropTypes.oneOf([',', '.', true, false]),
    decimalSeperator: PropTypes.oneOf([',', '.', true, false]),
    displayType: PropTypes.oneOf(['input', 'text']),
    prefix: PropTypes.string,
    suffix: PropTypes.string,
    format: PropTypes.oneOfType([PropTypes.string, PropTypes.func]),
    mask: PropTypes.string,
    value: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    touched: PropTypes.oneOfType([PropTypes.bool]),
    errorMsg: PropTypes.string,
    onChange: PropTypes.func.isRequired,
    onBlur: PropTypes.func,
    maxLength: PropTypes.number,
    require: PropTypes.bool,
  };

  static defaultProps = {
    thousandSeparator: false,
    displayType: 'input',
    prefix: '',
    suffix: '',
    format: null,
    mask: '',
    decimalSeperator: '.',
    value: null,
    touched: null,
    errorMsg: '',
    onBlur: noop,
    maxLength: 0,
    require: false,
  };

  state = {
    value: '',
    numAsString: '',
  };

  static getDerivedStateFromProps(props, state) {
    if (props.value !== state.numAsString) {
      const data = formatInput(props.value, props);

      return {
        value: data.formattedValue,
        numAsString: data.value,
      };
    }

    return null;
  }

  onChange = e => {
    e.persist();
    const inputValue = toString(e.target.value);
    const { formattedValue, value } = formatInput(inputValue, this.props);
    let cursorPos = e.target.selectionStart;

    this.setState({ value: formattedValue }, () => {
      cursorPos = getCursorPosition(
        this.props,
        inputValue,
        formattedValue,
        cursorPos,
      );
      setCaretPosition(e, cursorPos);
      this.props.onChange && this.props.onChange(e.target.name, value);
    });

    return value;
  };

  render() {
    const {
      name,
      label,
      touched,
      errorMsg,
      onBlur,
      displayType,
      require,
    } = this.props;

    if (displayType === 'text') {
      return <span>{this.state.value}</span>;
    }

    return (
      <Input
        require={require}
        name={name}
        label={label}
        touched={touched}
        errorMsg={errorMsg}
        value={this.state.value}
        onChange={this.onChange}
        onBlur={onBlur}
      />
    );
  }
}

export default NumberInput;

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import { isEmpty } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import Input from '../Input';
import IconButton from '../Button/IconButton';
import s from './ImageField.css';

class ImageField extends Component {
  static propTypes = {
    onChange: PropTypes.func.isRequired,
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    allowUpload: PropTypes.bool,
    touched: PropTypes.bool,
    errorMsg: PropTypes.string,
  };

  static defaultProps = {
    allowUpload: false,
    touched: false,
    errorMsg: '',
  };

  get isError() {
    const { touched, errorMsg } = this.props;

    return touched && !isEmpty(errorMsg);
  }

  handleFieldChange = event => {
    const file = event.target.files;
    if (file.length > 0) {
      const windowURL = window.URL || window.webkitURL;
      this.props.onChange &&
        this.props.onChange(
          this.props.name,
          windowURL.createObjectURL(file[0]),
        );
    }
  };

  openFileChooser = () => {
    this.inputElement.value = null;
    this.inputElement.click();
  };

  inputRef = element => {
    this.inputElement = element;
  };

  render() {
    const { value, name, label, allowUpload, touched, errorMsg } = this.props;
    const labelClasses = classNames(s.label, !isEmpty(value) && s.hasValue);
    const containerClass = classNames(s.container, {
      [s.errorContainer]: this.isError,
    });
    const captureProps = !allowUpload ? { capture: true } : {};

    return (
      <div className={containerClass}>
        <Input
          {...captureProps}
          style={{ position: 'relative', top: 0 }}
          type="file"
          accept="image/*"
          name={name}
          touched={touched}
          errorMsg={errorMsg}
          className={s.input}
          ref={this.inputRef}
          onChange={this.handleFieldChange}
        />
        <span className={labelClasses}>{label}</span>
        {!isEmpty(value) && (
          <span
            className={s.tapEdit}
            onClick={this.openFileChooser}
            onKeyDown={this.openFileChooser}
            role="button"
            tabIndex="0"
          >
            Nhấn vào để chỉnh sửa
          </span>
        )}
        <IconButton
          className={s.icon}
          primary
          onClick={this.openFileChooser}
          icon={value ? null : 'photo_camera'}
        >
          {!!value && <img src={value} alt="img" />}
        </IconButton>
      </div>
    );
  }
}

export default withStyles(s)(ImageField);

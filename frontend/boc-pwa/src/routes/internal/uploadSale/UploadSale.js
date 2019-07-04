import React, { PureComponent } from 'react';
import { Button, Popover } from 'homecredit-ui';
import { withFormik } from 'formik';
import { compose } from 'redux';
import { connect } from 'react-redux';
import { func, shape, bool, string } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import noop from 'lodash/noop';
import split from 'lodash/split';
import last from 'lodash/last';
import { alerts } from 'actions/internal/app';

import { getValidationSchema } from 'utils/validations';
import { uploadSale } from 'actions/internal';
import history from '../../../history';
import s from './UploadSale.css';

import {
  UPLOAD_TITLE,
  UPLOAD_BUTTON,
  UPLOAD_NOTE,
  SUPPORTED_FILE,
  ERROR_FILE_TYPE,
  POPUP_ERROR,
  INPUT_PLACEHOLDER,
  FIELD_NAME,
} from './constant';

const acceptFiles = SUPPORTED_FILE.map(file => `.${file}`).join(',');

class UploadSale extends PureComponent {
  static propTypes = {
    values: shape({
      filename: string,
    }),
    uploadSaleAction: func,
    touched: shape({
      filename: bool,
    }),
    errors: shape({
      filename: string,
    }),
    handleChange: func,
    handleBlur: func,
    setFieldTouched: func.isRequired,
    alertWarn: func,
    setFieldError: func,
  };

  static defaultProps = {
    values: {},
    uploadSaleAction: noop,
    touched: {},
    errors: {},
    handleChange: noop,
    handleBlur: noop,
    alertWarn: noop,
    setFieldError: noop,
  };

  constructor(props) {
    super(props);
    this.state = {
      selectedFile: null,
    };
  }

  handleChange = e => {
    const { handleChange } = this.props;
    const { target } = e;

    handleChange(e);
    if (target.value.length > 0) {
      this.setState({
        selectedFile: e.target.files[0],
      });
    } else {
      this.setState({ selectedFile: null });
    }
  };

  handleSubmit = e => {
    e.preventDefault();
    const { selectedFile } = this.state;
    const {
      setFieldTouched,
      uploadSaleAction,
      alertWarn,
      setFieldError,
    } = this.props;

    setFieldTouched(FIELD_NAME);

    if (!selectedFile) {
      setFieldError(FIELD_NAME);
      return;
    }
    const fileType = last(split(selectedFile.name, '.'));

    if (!SUPPORTED_FILE.includes(fileType)) {
      alertWarn(ERROR_FILE_TYPE);
      return;
    }

    if (selectedFile) {
      uploadSaleAction(selectedFile, history);
    }
  };

  handleOpenInputFile = () => {
    document.getElementById(FIELD_NAME).click();
  };

  uploadStatus = () => {
    const { errors, touched } = this.props;
    if (errors[FIELD_NAME]) {
      return (
        <Popover
          component={
            <i className={`material-icons ${s.statusIcon} ${s.inputFileError}`}>
              error_outlined
            </i>
          }
          placement="left"
        >
          {POPUP_ERROR}
        </Popover>
      );
    } else if (!errors[FIELD_NAME] && touched[FIELD_NAME]) {
      return (
        <i className={`material-icons ${s.statusIcon} ${s.inputFileSuccess}`}>
          check
        </i>
      );
    }
    return null;
  };

  render() {
    const {
      errors,
      handleBlur,
      values: { filename },
    } = this.props;
    const { selectedFile } = this.state;
    const inputStyle = errors[FIELD_NAME]
      ? s.inputFileFieldError
      : s.inputFileFieldNormal;

    return (
      <div className={s.container}>
        <div className={s.uploadForm}>
          <h1 className={s.title}>{UPLOAD_TITLE}</h1>
          <div className={`${s.inputFileField} ${inputStyle}`}>
            <div className={s.inputFileText} onClick={this.handleOpenInputFile}>
              <div>
                <i className={`material-icons ${s.icon}`}>cloud_upload</i>
              </div>
              <div className={s.text}>
                {(selectedFile && selectedFile.name) || INPUT_PLACEHOLDER}
              </div>
            </div>
            <div className={s.popUp}>{this.uploadStatus()}</div>
            <input
              id="filename"
              type="file"
              style={{ display: 'none' }}
              accept={acceptFiles}
              name="filename"
              value={filename}
              onChange={this.handleChange}
              onBlur={handleBlur}
            />
          </div>

          <div className={s.groupBtn}>
            <Button onClick={this.handleSubmit}>{UPLOAD_BUTTON}</Button>
          </div>
          <div className={s.note}>
            <span> {UPLOAD_NOTE.title} </span>
            <ul>
              {UPLOAD_NOTE.notes.map(item => (
                <li key={item.id}> {item.text}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    );
  }
}

const mapDispatchToProps = dispatch => ({
  uploadSaleAction: file => {
    dispatch(uploadSale(file));
  },
  alertWarn: message => {
    dispatch(alerts.warn(message));
  },
});

const enhance = compose(
  connect(
    null,
    mapDispatchToProps,
  ),
  withFormik({
    mapPropsToValues: () => ({ filename: '' }),
    validationSchema: getValidationSchema([FIELD_NAME]),
  }),
  withStyles(s),
);

export default enhance(UploadSale);

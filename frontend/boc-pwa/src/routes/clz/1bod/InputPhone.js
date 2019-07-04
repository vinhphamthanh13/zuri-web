/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
/* eslint-disable jsx-a11y/anchor-is-valid, jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions */

import React, { Component } from 'react';
import { compose } from 'redux';
import {
  objectOf,
  any,
  func,
  object,
  bool,
  oneOfType,
  number,
} from 'prop-types';
import { connect } from 'react-redux';
import { withFormik } from 'formik';
import { get, pick, isEmpty, noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { NumberInput } from 'homecredit-ui';

import { getValidationSchema } from 'utils/validations';
import { generateOTP } from 'actions/clz';
import { FORM_STATUS, LOADER_STATUS } from 'constants/common';
import { sendGAEvent } from 'utils/ga';

import { INPUT_PHONE_FIELDS, INPUT_PHONE_TEXTS } from './constant';
import { mapStateToProps, mapDispatchToProps } from './commonProps';

import s from './InputPhone.css';

class InputPhone extends Component {
  static propTypes = {
    formStatus: number,
    errors: objectOf(any),
    values: objectOf(any),
    setFieldValue: func,
    setFieldTouched: func.isRequired,
    onChange1BODInfo: func,
    updateFormStatus: func,
    validateForm: func,
    handleBlur: func,
    goToNextStep: func,
    getOTPSuccess: func,
    updateLoadingStatus: func,
    touched: oneOfType([bool, object]),
    globalPopup: objectOf(any),
  };

  static defaultProps = {
    errors: {},
    values: {},
    touched: {},
    setFieldValue: noop,
    onChange1BODInfo: noop,
    handleBlur: noop,
    updateFormStatus: noop,
    validateForm: noop,
    goToNextStep: noop,
    getOTPSuccess: noop,
    updateLoadingStatus: noop,
    formStatus: FORM_STATUS.INITIAL,
    globalPopup: {},
  };

  /**
   * Update Form Status to initial after render
   *
   * @return {void}
   */
  componentDidMount = () => {
    this.props.updateFormStatus(FORM_STATUS.INITIAL);

    sendGAEvent('Visit_Input_phone', 'Input_phone', 'Input Phone');
  };

  /**
   * Validate when form status is changed
   *
   * @return {void}
   */
  async componentWillReceiveProps(nextProps) {
    const {
      updateFormStatus,
      formStatus,
      validateForm,
      goToNextStep,
      getOTPSuccess,
      setFieldTouched,
      values,
      globalPopup,
      updateLoadingStatus,
    } = this.props;
    const { formStatus: nextStatus } = nextProps;
    const phoneNumber = get(values, INPUT_PHONE_FIELDS.name);

    if (formStatus === FORM_STATUS.INITIAL && formStatus !== nextStatus) {
      const errors = await validateForm();
      if (isEmpty(errors)) {
        goToNextStep();

        updateLoadingStatus(LOADER_STATUS.ON);
        // Call generate OTP to get verification ID
        generateOTP(phoneNumber).then(response => {
          updateLoadingStatus(LOADER_STATUS.OFF);
          if (isEmpty(response.errors)) {
            getOTPSuccess(response);
          } else {
            globalPopup.open();
          }
        });
      } else {
        updateFormStatus(FORM_STATUS.INITIAL);
        Object.keys(errors).forEach(error => setFieldTouched(error));
      }
    }
  }

  /**
   * Update Form Field Input
   *
   * @return {void}
   */
  onFieldChange = (name, value) => {
    this.updateFormInfo(name, value);
  };

  /**
   * Update Form Field Value & Redux State
   *
   * @param  {String} name  Field Name
   * @param  {String} value Field Value
   *
   * @return {Void}
   */
  updateFormInfo = (name, value) => {
    const { setFieldValue, onChange1BODInfo } = this.props;

    // Update Form Value
    setFieldValue(name, value);

    // Update Redux state
    onChange1BODInfo(name, value);
  };

  handleBlur = e => {
    sendGAEvent('Input_phone', 'Phone', 'Input Phone');

    this.props.handleBlur(e);
  };

  render() {
    const { values, touched, errors } = this.props;
    const { name, type, label } = INPUT_PHONE_FIELDS;
    const phoneNumber = get(values, name);

    return (
      <div className="container">
        <div className="row center">
          <div className="col-md-10">
            <p className={s.title}>{INPUT_PHONE_TEXTS.TITLE}</p>
            <NumberInput
              id={name}
              key={name}
              type={type}
              label={label}
              name={name}
              value={phoneNumber}
              onChange={this.onFieldChange}
              onBlur={this.handleBlur}
              touched={touched[name]}
              errorMsg={errors[name]}
              maxLength={10}
              className={s.inputPhone}
              msgPosition="below"
            />
          </div>
        </div>
      </div>
    );
  }
}

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withFormik({
    mapPropsToValues: props => pick(props.firstBODInfo, ['phoneNumber']),
    validationSchema: getValidationSchema(['phoneNumber']),
  }),
  withStyles(s),
);

export default enhance(InputPhone);

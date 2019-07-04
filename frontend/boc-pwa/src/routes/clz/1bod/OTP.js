/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React, { Component, Fragment } from 'react';
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
import { Checkbox, NumberInput } from 'homecredit-ui';
import OtpCountDown from 'components/common/OTPCountDown';
import Modal from 'components/common/Modal';

import { getValidationSchema } from 'utils/validations';

import { verifyOTP, generateOTP, trackingInfo } from 'actions/clz';
import { FORM_STATUS, LOADER_STATUS } from 'constants/common';
import { sendGAEvent } from 'utils/ga';

import {
  INPUT_OTP_FIELDS,
  OTP_TEXTS,
  ACCEPTED_TERM_FIELDS,
  MAX_ATTEMPT,
  MAX_RETRY,
  STEPS,
  STEP_NAME,
  OTP_COUNT_SECOND,
} from './constant';
import s from './OTP.css';
import { mapStateToProps, mapDispatchToProps } from './commonProps';

class OTP extends Component {
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
    updateStep: func,
    touched: oneOfType([bool, object]),
    globalPopup: objectOf(any),
    firstBODInfo: objectOf(any),
    getTrackingToken: func.isRequired,
  };

  static defaultProps = {
    formStatus: FORM_STATUS.INITIAL,
    errors: {},
    values: {},
    setFieldValue: noop,
    onChange1BODInfo: noop,
    handleBlur: noop,
    updateFormStatus: noop,
    validateForm: noop,
    goToNextStep: noop,
    getOTPSuccess: noop,
    updateLoadingStatus: noop,
    updateStep: noop,
    touched: {},
    globalPopup: {},
    firstBODInfo: {},
  };

  constructor(props) {
    super(props);
    this.state = {
      isOpenModal: false,
      isOpenResendModal: false,
      isOpenAttempModal: false,
      isOTPExpire: false,
      attempt: 0,
      retry: 0,
    };
  }

  /**
   * Update Form Status to initial after render
   *
   * @return {void}
   */
  componentDidMount() {
    this.props.updateFormStatus(FORM_STATUS.INITIAL);

    sendGAEvent('Visit_input_OTP', 'Input_OTP', 'Input OTP');
  }

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
      setFieldTouched,
      updateLoadingStatus,
      values,
    } = this.props;
    const { formStatus: nextStatus } = nextProps;
    const { isOTPExpire } = this.state;

    if (formStatus === FORM_STATUS.INITIAL && formStatus !== nextStatus) {
      const errors = await validateForm();
      // Track forget to click consent
      !values.acceptedTerm && sendGAEvent('Agree_TnC', 'TnC', 'Input OTP');

      // Show error when OTP is expired, skip validate otp
      if (isOTPExpire) {
        updateFormStatus(FORM_STATUS.INITIAL);
        this.setState({ isOpenAttempModal: true });
        return;
      }

      if (isEmpty(errors)) {
        // Show loading
        updateLoadingStatus(LOADER_STATUS.ON);
        // Calling verify OTP API
        this.verifyOTP();
      } else {
        updateFormStatus(FORM_STATUS.INITIAL);
        Object.keys(errors).forEach(error => setFieldTouched(error));
      }
    }
  }

  /**
   * Clear valud in redux state before unmount
   *
   * @return {void}
   */
  componentWillUnmount() {
    this.updateFormInfo(INPUT_OTP_FIELDS.name, '');
    this.updateFormInfo(ACCEPTED_TERM_FIELDS.name, false);
  }

  /**
   * Update Form Field Checkbox
   *
   * @return {void}
   */
  onCheckedChange = value => {
    this.updateFormInfo(ACCEPTED_TERM_FIELDS.name, value);
  };

  /**
   * Update state when modal is opened
   */
  onOpenModal = () => {
    this.setState({
      isOpenModal: true,
    });

    sendGAEvent('View_TnC', 'TnC', 'Input OTP');
  };

  /**
   * Update state when modal is closed
   */
  onCloseModal = () => {
    this.setState({
      isOpenModal: false,
    });
  };

  /**
   * Update state when modal is opened
   */
  onOpenResendModal = () => {
    const {
      values,
      getOTPSuccess,
      globalPopup,
      updateLoadingStatus,
    } = this.props;

    sendGAEvent('Regenerate_OTP', 'OTP', 'Input OTP');

    updateLoadingStatus(LOADER_STATUS.ON);
    generateOTP(values.phoneNumber).then(response => {
      updateLoadingStatus(LOADER_STATUS.OFF);
      if (isEmpty(response.errors)) {
        getOTPSuccess(response);
        this.setState({
          isOpenResendModal: true,
          retry: this.state.retry + 1,
          attempt: 0,
          isOTPExpire: false,
        });
      } else {
        globalPopup.open();
      }
    });
  };

  /**
   * Update state when modal is closed
   */
  onCloseResendModal = () => {
    this.isMaxRetry
      ? this.props.updateStep(STEPS.INPUT_PHONE)
      : this.setState({
          isOpenResendModal: false,
        });
  };

  /**
   * Update state when modal is opened
   */
  onOpenAttempModal = () => {
    sendGAEvent('Incorrect_message', 'OTP', 'Input OTP');

    if (!this.state.isOpenAttempModal) {
      this.props.updateFormStatus(FORM_STATUS.INITIAL);
      this.setState({
        isOpenAttempModal: true,
        attempt: this.state.attempt + 1,
      });
    }
  };

  /**
   * Update state when modal is closed
   */
  onCloseAttempModal = () => {
    if (this.isMaxAttempt) {
      this.props.updateStep(STEPS.INPUT_PHONE);
    } else {
      this.setState({
        isOpenAttempModal: false,
      });
    }
  };

  /**
   * Update Form Field Input
   *
   * @return {void}
   */
  onFieldChange = (name, value) => {
    this.updateFormInfo(name, value);
  };

  get isMaxRetry() {
    return MAX_RETRY - this.state.retry <= 0;
  }

  get isMaxAttempt() {
    return MAX_ATTEMPT - this.state.attempt <= 0;
  }

  /**
   * Call API - Verify OTP
   *
   * @return {void}
   */
  verifyOTP = () => {
    const {
      goToNextStep,
      globalPopup,
      updateLoadingStatus,
      firstBODInfo,
      getTrackingToken,
    } = this.props;
    const { phoneNumber, otp, verificationID } = firstBODInfo;

    verifyOTP(phoneNumber, verificationID, otp).then(response => {
      updateLoadingStatus(LOADER_STATUS.OFF);
      if (isEmpty(response.errors)) {
        const isMatch = get(response, 'data.status');
        if (isMatch === 'MATCHED') {
          goToNextStep();
          const {
            creditAmount,
            bestOffer,
            retailAgent,
            sa,
            tenor,
            partnerCode,
            tipperCode,
          } = firstBODInfo;
          const annualInterestRate = get(
            bestOffer,
            'celOffer.annualInterestRate',
          );
          const firstInstallmentDueDate = get(
            bestOffer,
            'celOffer.firstInstallmentDueDate',
          );
          const offerCode = get(bestOffer, 'offerCode');
          const productCode = get(bestOffer, 'productInformation.code');
          const productType = get(bestOffer, 'productInformation.type');
          const monthlyPayment = get(
            bestOffer,
            'celOffer.monthlyInstallmentAmount.amount',
          );
          const trackingToken = get(response, 'token.token');
          const data = {
            url: STEP_NAME[STEPS.CALCULATE_CASH],
            token: trackingToken,
            body: {
              annualInterestRate,
              creditAmount,
              firstInstallmentDueDate,
              monthlyPayment,
              offerCode,
              productCode,
              productType,
              retailAgent,
              sa,
              tenor,
              partnerCode,
              tipperCode,
            },
          };
          trackingInfo(data);
          getTrackingToken(trackingToken);
        } else {
          this.onOpenAttempModal();
        }
      } else {
        globalPopup.open();
      }
    });
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

  /**
   * Hanlde time out OTP
   */
  handleTimeoutOTP = () => {
    this.setState({
      isOTPExpire: true,
    });
  };

  handleBlur = e => {
    sendGAEvent('Input_OTP', 'Input_OTP', 'Input OTP');

    this.props.handleBlur(e);
  };

  render() {
    const { values, touched, errors } = this.props;
    const {
      isOpenModal,
      isOpenResendModal,
      isOpenAttempModal,
      attempt,
      retry,
      isOTPExpire,
    } = this.state;
    const { name, type, label } = INPUT_OTP_FIELDS;
    const otp = get(values, name);
    const phoneNumber = !isEmpty(values.phoneNumber)
      ? values.phoneNumber.replace(/(\d{4})\d{3}/, '$1xxx')
      : '';
    const attempRemain = MAX_ATTEMPT - attempt;
    const resendRemain =
      MAX_RETRY - retry > 1 ? `0${MAX_RETRY - retry - 1}` : '0';

    return (
      <div className="container">
        <div className="row center">
          <div className="col-md-10">
            <p className={s.title}>
              {`${OTP_TEXTS.DESCRIPTION}`}
              <b>{`${phoneNumber}`}</b>
            </p>
            <div className={s.otpWrapper}>
              <NumberInput
                autoFocus
                id={name}
                key={name}
                type={type}
                label={label}
                name={name}
                value={otp}
                onChange={this.onFieldChange}
                onBlur={this.handleBlur}
                touched={touched[name]}
                errorMsg={errors[name]}
                maxLength={8}
                disabled={isOTPExpire}
                msgPosition="below"
              />
            </div>
            <OtpCountDown
              start={!isOTPExpire}
              disabled={isOTPExpire}
              seconds={OTP_COUNT_SECOND}
              setOTPTimeOut={this.handleTimeoutOTP}
              resendOTP={this.onOpenResendModal}
              retry={MAX_RETRY}
            />
            <Checkbox
              className={s.checkBox}
              checked={values.acceptedTerm}
              label={OTP_TEXTS.TERM}
              onChange={this.onCheckedChange}
              error={!!errors.acceptedTerm && touched.acceptedTerm}
            >
              <Modal
                onOpen={this.onOpenModal}
                onClose={this.onCloseModal}
                isOpen={isOpenModal}
                toggleButton={
                  <a>
                    <b>{OTP_TEXTS.CONDITION}</b>
                  </a>
                }
                footer={
                  <div className={s.modalFooter} onClick={this.onCloseModal}>
                    <h4>{OTP_TEXTS.AGREE}</h4>
                  </div>
                }
              >
                <div className={s.resendBody}>
                  <h3>Điều khoản và Điều kiện</h3>
                  <p className={s.termText}>{OTP_TEXTS.TERM_CONDITION}</p>
                </div>
              </Modal>
            </Checkbox>
            <Modal
              onOpen={this.onOpenResendModal}
              onClose={this.onCloseResendModal}
              isOpen={isOpenResendModal}
              footer={
                <div
                  className={s.modalFooter}
                  onClick={this.onCloseResendModal}
                >
                  <h4>{OTP_TEXTS.AGREE}</h4>
                </div>
              }
            >
              <div className={s.resendBody}>
                <h3>Lưu ý</h3>
                {this.isMaxRetry ? (
                  <Fragment>
                    <p>{OTP_TEXTS.MAXIMUM_TRIED_1}</p>
                    <p>{OTP_TEXTS.MAXIMUM_TRIED_2}</p>
                  </Fragment>
                ) : (
                  <Fragment>
                    Bạn còn <span className={s.resendText}>{resendRemain}</span>{' '}
                    lần Gửi lại Mã Xác Thực
                  </Fragment>
                )}
              </div>
            </Modal>
            <Modal
              onOpen={this.onOpenAttempModal}
              onClose={this.onCloseAttempModal}
              isOpen={isOpenAttempModal}
              footer={
                <div
                  className={s.modalFooter}
                  onClick={this.onCloseAttempModal}
                >
                  <h4>{OTP_TEXTS.AGREE}</h4>
                </div>
              }
            >
              <div className={s.resendBody}>
                <h3>Lưu ý</h3>
                {isOTPExpire &&
                  attempt < MAX_ATTEMPT && <p>{OTP_TEXTS.OTP_DISABLED}</p>}
                {(!isOTPExpire || (isOTPExpire && attempt === MAX_ATTEMPT)) && (
                  <AttempContent
                    isMaxAttempt={this.isMaxAttempt}
                    attempRemain={attempRemain}
                  />
                )}
              </div>
            </Modal>
          </div>
        </div>
      </div>
    );
  }
}

const AttempContent = ({ isMaxAttempt, attempRemain }) =>
  isMaxAttempt ? (
    <Fragment>
      <p>
        {OTP_TEXTS.MAXIMUM_ATTEMP_1}
        <span className={s.resendText}>{`0${MAX_ATTEMPT}`}</span>
        {OTP_TEXTS.MAXIMUM_ATTEMP_2}
      </p>
      <p>{OTP_TEXTS.MAXIMUM_ATTEMP_3}</p>
    </Fragment>
  ) : (
    <Fragment>
      <p>{OTP_TEXTS.ATTEMP_1}</p>
      <p>
        {OTP_TEXTS.ATTEMP_2}
        <span className={s.resendText}>{`0${attempRemain}`}</span>
        {OTP_TEXTS.ATTEMP_3}
      </p>
    </Fragment>
  );
AttempContent.propTypes = {
  isMaxAttempt: bool.isRequired,
  attempRemain: number.isRequired,
};

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withFormik({
    validateOnBlur: false,
    mapPropsToValues: props =>
      pick(props.firstBODInfo, [
        'phoneNumber',
        'otp',
        'acceptedTerm',
        'verificationID',
      ]),
    validationSchema: getValidationSchema(['otp', 'acceptedTerm']),
  }),
  withStyles(s),
);

export default enhance(OTP);

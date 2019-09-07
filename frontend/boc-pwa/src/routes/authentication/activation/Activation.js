/* eslint-disable no-undef */

import React, { Component } from 'react';
import { func, objectOf, any, bool } from 'prop-types';
import { get } from 'lodash';
import { connect } from 'react-redux';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Recaptcha from 'react-recaptcha';
import { withFormik } from 'formik/dist/index';
import Header from 'components/Header';
import Button from 'components/Button';
import Input from 'components/Input';
import { activation } from 'constants/schemas';
import {
  goBack,
  navigateTo,
  injectGoogleCaptchaScript,
  getLocationState,
} from 'utils/browser';
import {
  REGEXP,
  GOOGLE_CAPTCHA_SITE_KEY,
  G_CAPTCHA_ID,
  INIT_USER,
  LS_REGISTER,
} from 'constants/common';
import { ROUTER_URL } from 'constants/routerUrl';
import { HTTP_STATUS } from 'constants/http';
import { activationProps } from '../commonProps';
import s from './Activation.css';

const LOGIN = false;
const REGISTER = true;
const PHONE_FIELD = 'phoneNumber';
const LOGIN_MESSAGE = 'Hãy nhập số điện thoại đã đăng ký BOCVN.';
const REGISTER_MESSAGE =
  'Bạn đã đăng ký cửa hàng trên hệ thống BOCVN. Hãy nhập số điện thoại chưa được đăng ký.';

class Activation extends Component {
  static propTypes = {
    values: objectOf(any).isRequired,
    errors: objectOf(any),
    touched: objectOf(bool).isRequired,
    isValid: bool.isRequired,
    creatingUser: bool,
    handleChange: func.isRequired,
    setFieldValue: func.isRequired,
    setFieldTouched: func.isRequired,
    handleSubmit: func.isRequired,
    existingUser: objectOf(any),
    dispatchExistingUser: func.isRequired,
    dispatchError: func.isRequired,
    dispatchExistingUserAction: func.isRequired,
  };

  static defaultProps = {
    errors: {},
    existingUser: INIT_USER,
    creatingUser: null,
  };

  constructor(props) {
    super(props);
    this.state = {
      sendingOTPStatus: false,
      existingUser: INIT_USER,
      creatingUser: null,
      gCaptchaStatus: true,
    };
    this.recaptchaRef = React.createRef();
    injectGoogleCaptchaScript(document, 'script', G_CAPTCHA_ID);
  }

  static getDerivedStateFromProps(props, state) {
    const { sendingOTPStatus, existingUser, creatingUser } = props;
    const {
      sendingOTPStatus: cachedSendingOTPStatus,
      existingUser: cachedExistingUser,
      creatingUser: cachedCreatingUser,
    } = state;
    if (
      sendingOTPStatus !== cachedSendingOTPStatus ||
      existingUser.success !== cachedExistingUser.success ||
      creatingUser !== cachedCreatingUser
    ) {
      return {
        sendingOTPStatus,
        existingUser,
        creatingUser,
      };
    }

    return null;
  }

  componentDidMount() {
    this.clearCachedData();
  }

  componentDidUpdate(prevProps) {
    const { dispatchError, errors, dispatchExistingUserAction } = prevProps;
    const { sendingOTPStatus, existingUser, creatingUser } = this.state;
    const isRegistering = getLocationState(LS_REGISTER);
    const { code, success, message } = existingUser;

    if (isRegistering && creatingUser) {
      navigateTo(ROUTER_URL.AUTH.CREATING_STORE);
    }

    if (isRegistering && errors[PHONE_FIELD]) {
      dispatchExistingUserAction(INIT_USER);
    }
    // Creating account with registered phone will prompt error
    if (
      isRegistering &&
      Object.is(success, REGISTER) &&
      code !== HTTP_STATUS.INTERNAL_ERROR
    ) {
      dispatchError(REGISTER_MESSAGE);
      this.clearCachedData();
    }
    // Login with un-registered phone will prompt error
    if (
      !isRegistering &&
      Object.is(success, LOGIN) &&
      code !== HTTP_STATUS.INTERNAL_ERROR
    ) {
      dispatchError(`${message} ${LOGIN_MESSAGE}`);
      this.clearCachedData();
    }

    // Redirect to verify OTP
    if (sendingOTPStatus) {
      navigateTo(ROUTER_URL.AUTH.VERIFYING_OTP);
    }
  }

  componentWillUnmount() {
    this.clearCachedData();
  }

  clearCachedData = () => {
    const { setFieldValue, dispatchExistingUserAction } = this.props;
    setFieldValue(PHONE_FIELD, '');
    dispatchExistingUserAction(INIT_USER);
  };

  handleVerifyCaptcha = response => {
    const { dispatchExistingUser, values } = this.props;
    const phoneNumber = get(values, PHONE_FIELD);
    this.handleCaptchaStatus(true);
    if (response) dispatchExistingUser(phoneNumber);
  };

  handleExpiredCaptcha = () => {
    this.handleCaptchaStatus(false);
  };

  handleCaptchaStatus = value => {
    this.setState({
      gCaptchaStatus: value,
    });
  };

  render() {
    const {
      values: { countryCode, phoneNumber },
      handleChange,
      handleSubmit,
      errors,
      isValid,
      setFieldTouched,
      touched,
      existingUser,
    } = this.props;
    const { gCaptchaStatus } = this.state;

    const registerState = getLocationState(LS_REGISTER);
    const headerTitle = registerState
      ? 'Số điện thoại cửa hàng'
      : 'đăng nhập cửa hàng';
    const code = get(existingUser, 'code');
    const isLoginValid = isValid && !registerState && existingUser.success;
    const isRegisterValid = isValid && !existingUser.success;
    const isActivatingCode =
      code !== HTTP_STATUS.INTERNAL_ERROR &&
      (isLoginValid || isRegisterValid) &&
      gCaptchaStatus;
    const activatingLabel = registerState ? 'Tạo cửa hàng' : 'Lấy mã xác nhận';

    return (
      <>
        <div className={s.container}>
          <Header title={headerTitle} iconLeft onClickLeft={goBack} />
          <form onSubmit={handleSubmit}>
            <div className={s.inputs}>
              <Input
                name="countryCode"
                value={countryCode}
                onChange={handleChange}
                className={s.countryCode}
                disabled
              />
              <Input
                name={PHONE_FIELD}
                type="tel"
                value={phoneNumber}
                placeholder="Số điện thoại"
                onChange={handleChange}
                className={s.phoneNumber}
                errors={errors}
                onTouch={setFieldTouched}
                touched={touched}
                disabled={isLoginValid}
              />
            </div>
            <Button
              label={activatingLabel}
              type="submit"
              disabled={!isActivatingCode}
            />
          </form>
          {isValid && (
            <div className={s.captcha}>
              <Recaptcha
                ref={this.recaptchaRef}
                sitekey={GOOGLE_CAPTCHA_SITE_KEY}
                render="explicit"
                verifyCallback={this.handleVerifyCaptcha}
                expiredCallback={this.handleExpiredCaptcha}
                hl="vi"
              />
            </div>
          )}
        </div>
      </>
    );
  }
}

const enhancers = [
  withFormik({
    validateOnBlur: true,
    isInitialValid: false,
    mapPropsToValues: props => ({
      countryCode: props.code,
      phoneNumber: '',
    }),
    validationSchema: activation,
    handleSubmit: (
      values,
      {
        props: {
          dispatchSendOTP,
          dispatchSetPhoneNumber,
          dispatchCreatingUser,
        },
      },
    ) => {
      const registerUser = getLocationState(LS_REGISTER);
      const countryCode = get(values, 'countryCode');
      const sanitizedCode = countryCode.replace(/\+/, '');
      const phoneNumber = get(values, PHONE_FIELD);
      const registerPhoneNumber = `${phoneNumber.replace(/^(\d+)/, '$1')}`;
      const encryptPhone = registerPhoneNumber.replace(
        REGEXP.ENCRYPT_PHONE,
        (_, p1, p2, p3) => `${p1}${p2.replace(/\d/g, 'x')}${p3}`,
      );
      if (registerUser) {
        dispatchCreatingUser(phoneNumber);
        dispatchSetPhoneNumber({
          countryCode: sanitizedCode,
          phoneNumber,
          encryptPhone,
        });
      } else {
        dispatchSetPhoneNumber({
          countryCode: sanitizedCode,
          phoneNumber,
          encryptPhone,
        });
        dispatchSendOTP(sanitizedCode, phoneNumber);
      }
    },
  }),
  withStyles(s),
];

export default connect(
  activationProps.mapStateToProps,
  activationProps.mapDispatchToProps,
)(compose(...enhancers)(Activation));

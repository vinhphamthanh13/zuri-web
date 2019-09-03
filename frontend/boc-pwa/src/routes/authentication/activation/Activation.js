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
import { goBack, injectGoogleCaptchaScript } from 'utils/browser';
import {
  REGEXP,
  GOOGLE_CAPTCHA_SITE_KEY,
  G_CAPTCHA_ID,
  INIT_USER,
} from 'constants/common';
import { ROUTER_URL } from 'constants/routerUrl';
import { HTTP_STATUS } from 'constants/http';
import history from '../../../history';
import { activationProps } from '../commonProps';
import s from './Activation.css';

const LOGIN = false;
const REGISTER = true;
const PHONE_FIELD = 'phoneNumber';
const LOCATION_STATE = 'location.state';
const LOGIN_MESSAGE = 'Hãy nhập số điện thoại đã đăng ký BOCVN.';
const REGISTER_MESSAGE =
  'Bạn đã đăng ký cửa hàng trên hệ thống BOCVN. Hãy nhập số điện thoại chưa được đăng ký.';
const authUrl = ROUTER_URL.AUTHENTICATION;

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
      getVerificationCodeStatus: false,
      existingUser: INIT_USER,
      creatingUser: null,
      gCaptchaStatus: true,
    };
    this.recaptchaRef = React.createRef();
    injectGoogleCaptchaScript(document, 'script', G_CAPTCHA_ID);
  }

  static getDerivedStateFromProps(props, state) {
    const { getVerificationCodeStatus, existingUser, creatingUser } = props;
    const {
      getVerificationCodeStatus: cachedgetVerificationCodeStatus,
      existingUser: cachedExistingUser,
      creatingUser: cachedCreatingUser,
    } = state;
    if (
      getVerificationCodeStatus !== cachedgetVerificationCodeStatus ||
      existingUser.success !== cachedExistingUser.success ||
      creatingUser !== cachedCreatingUser
    ) {
      return {
        getVerificationCodeStatus,
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
    const {
      getVerificationCodeStatus,
      dispatchError,
      errors,
      dispatchExistingUserAction,
    } = prevProps;
    const {
      getVerificationCodeStatus: cachedgetVerificationCodeStatus,
      existingUser,
      creatingUser,
    } = this.state;
    const isRegistering = get(history, LOCATION_STATE);
    const { code, success, message } = existingUser;

    if (isRegistering && creatingUser) {
      history.push(authUrl.CREATING_NEW_STORE);
    }

    if (
      !isRegistering &&
      Object.is(success, LOGIN) &&
      code !== HTTP_STATUS.INTERNAL_ERROR
    ) {
      dispatchError(`${message} ${LOGIN_MESSAGE}`);
      this.clearCachedData();
    }

    if (isRegistering && errors[PHONE_FIELD]) {
      dispatchExistingUserAction(INIT_USER);
    }

    if (
      isRegistering &&
      Object.is(success, REGISTER) &&
      code !== HTTP_STATUS.INTERNAL_ERROR
    ) {
      dispatchError(REGISTER_MESSAGE);
      this.clearCachedData();
    }

    if (
      cachedgetVerificationCodeStatus &&
      getVerificationCodeStatus !== cachedgetVerificationCodeStatus
    ) {
      history.push(authUrl.VERIFY);
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

    const registerState = get(history, 'location.state.register');
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
          dispatchVerificationCode,
          dispatchSetPhoneNumber,
          dispatchCreatingUser,
        },
      },
    ) => {
      const registerUser = get(history, 'location.state');
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
      } else {
        dispatchSetPhoneNumber(encryptPhone);
        dispatchVerificationCode(sanitizedCode, phoneNumber);
      }
    },
  }),
  withStyles(s),
];

export default connect(
  activationProps.mapStateToProps,
  activationProps.mapDispatchToProps,
)(compose(...enhancers)(Activation));

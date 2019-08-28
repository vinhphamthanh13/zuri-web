import React, { Component } from 'react';
import { func, objectOf, any, bool } from 'prop-types';
import { get } from 'lodash';
import { connect } from 'react-redux';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { withFormik } from 'formik/dist/index';
import Header from 'components/Header';
import Button from 'components/Button';
import Input from 'components/Input';
import { activation } from 'constants/schemas';
import { goBack } from 'utils/browser';
import { REGEXP } from 'constants/common';
import history from '../../../history';
import { activationProps } from '../commonProps';
import s from './Activation.css';

const INIT_USER = {
  message: '',
  success: null,
};
const LOGIN = false;
const REGISTER = true;
const PHONE_FIELD = 'phoneNumber';
const LOCATION_STATE = 'location.state';
const LOGIN_MESSAGE = 'Nhập số điện thoại đã đăng ký BOCVN.';
const REGISTER_MESSAGE =
  'Bạn đã đăng ký cửa hàng trên hệ thống BOCVN. Hãy nhập số điện thoại chưa được đăng ký.';

class Activation extends Component {
  static propTypes = {
    values: objectOf(any).isRequired,
    errors: objectOf(any),
    touched: objectOf(bool).isRequired,
    isValid: bool.isRequired,
    handleChange: func.isRequired,
    setFieldValue: func.isRequired,
    setFieldTouched: func.isRequired,
    handleSubmit: func.isRequired,
    dispatchExistingUser: func.isRequired,
    existingUser: objectOf(any),
    dispatchError: func.isRequired,
    dispatchExistingUserAction: func.isRequired,
  };

  static defaultProps = {
    errors: {},
    existingUser: INIT_USER,
  };

  state = {
    getVerificationCodeStatus: false,
  };

  static getDerivedStateFromProps(props, state) {
    const { getVerificationCodeStatus, existingUser } = props;
    const {
      getVerificationCodeStatus: cachedgetVerificationCodeStatus,
      existingUser: cachedExistingUser,
    } = state;
    if (
      getVerificationCodeStatus !== cachedgetVerificationCodeStatus ||
      existingUser.success !== cachedExistingUser.success
    ) {
      return {
        getVerificationCodeStatus,
        existingUser,
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
      dispatchExistingUser,
      values,
      dispatchError,
      errors,
      dispatchExistingUserAction,
    } = prevProps;
    const {
      getVerificationCodeStatus: cachedgetVerificationCodeStatus,
      existingUser,
    } = this.state;
    const isRegistering = get(history, LOCATION_STATE);
    const phoneNumber = get(values, PHONE_FIELD);
    const { success, message } = existingUser;

    if (REGEXP.PHONE_NUMBER.test(phoneNumber) && Object.is(success, null)) {
      dispatchExistingUser(phoneNumber);
    }

    if (!isRegistering && Object.is(success, LOGIN)) {
      dispatchError(`${message} ${LOGIN_MESSAGE}`);
      this.clearCachedData();
    }

    if (isRegistering && errors[PHONE_FIELD]) {
      dispatchExistingUserAction(INIT_USER);
    }

    if (isRegistering && Object.is(success, REGISTER)) {
      dispatchError(REGISTER_MESSAGE);
      this.clearCachedData();
    }

    if (
      cachedgetVerificationCodeStatus &&
      getVerificationCodeStatus !== cachedgetVerificationCodeStatus
    ) {
      history.push('/verifyCode');
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

    const registerState = get(history, 'location.state.register');
    const headerTitle = registerState
      ? 'Số điện thoại cửa hàng'
      : 'đăng nhập cửa hàng';
    const isLoginValid = isValid && !registerState && existingUser.success;
    const isRegisterValid = isValid && !existingUser.success;
    const isActivatingCode = isLoginValid || isRegisterValid;
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
      { props: { dispatchVerificationCode, dispatchSetPhoneNumber } },
    ) => {
      const countryCode = get(values, 'countryCode');
      const sanitizedCode = countryCode.replace(/\+/, '');
      const phoneNumber = get(values, PHONE_FIELD);
      const registerPhoneNumber = `${phoneNumber.replace(/^(\d+)/, '$1')}`;
      const encryptPhone = registerPhoneNumber.replace(
        REGEXP.ENCRYPT_PHONE,
        (_, p1, p2, p3) => `${p1}${p2.replace(/\d/g, 'x')}${p3}`,
      );
      dispatchSetPhoneNumber(encryptPhone);
      dispatchVerificationCode(sanitizedCode, phoneNumber);
    },
  }),
  withStyles(s),
];

export default connect(
  activationProps.mapStateToProps,
  activationProps.mapDispatchToProps,
)(compose(...enhancers)(Activation));

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

class Activation extends Component {
  static propTypes = {
    values: objectOf(any).isRequired,
    errors: objectOf(any),
    touched: objectOf(bool).isRequired,
    isValid: bool.isRequired,
    handleChange: func.isRequired,
    setFieldTouched: func.isRequired,
    handleSubmit: func.isRequired,
    dispatchExistingUser: func.isRequired,
    existingUser: bool.isRequired,
  };

  static defaultProps = {
    errors: {},
  };

  state = {
    getVerificationCodeStatus: false,
  };

  static getDerivedStateFromProps(props, state) {
    const { getVerificationCodeStatus, values } = props;
    const {
      getVerificationCodeStatus: cachedgetVerificationCodeStatus,
      values: cachedValues,
    } = state;
    if (
      getVerificationCodeStatus !== cachedgetVerificationCodeStatus ||
      values !== cachedValues
    ) {
      const { phoneNumber } = values;
      return {
        getVerificationCodeStatus,
        phoneNumber,
      };
    }

    return null;
  }

  componentDidUpdate(prevProps) {
    const { getVerificationCodeStatus, dispatchExistingUser } = prevProps;
    const {
      getVerificationCodeStatus: cachedgetVerificationCodeStatus,
      phoneNumber,
    } = this.state;
    const registerUser = get(history, 'location.state.register');

    if (REGEXP.PHONE_NUMBER.test(phoneNumber) && !registerUser) {
      dispatchExistingUser(phoneNumber);
    }

    if (
      cachedgetVerificationCodeStatus &&
      getVerificationCodeStatus !== cachedgetVerificationCodeStatus
    ) {
      history.push('/verifyCode');
    }
  }

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
    const isLoginValid = isValid && !registerState && existingUser;
    const isRegisterValid = false;
    const isActivatingCode = isLoginValid || isRegisterValid;

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
                name="phoneNumber"
                type="tel"
                value={phoneNumber}
                placeholder="Số điện thoại"
                onChange={handleChange}
                className={s.phoneNumber}
                errors={errors}
                onTouch={setFieldTouched}
                touched={touched}
              />
            </div>
            <Button
              label="Lấy Mã Xác Nhận"
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
      const phoneNumber = get(values, 'phoneNumber');
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

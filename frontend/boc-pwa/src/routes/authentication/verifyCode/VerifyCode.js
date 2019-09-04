import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { func, objectOf, any, bool, string } from 'prop-types';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { get } from 'lodash';
import { withFormik } from 'formik';
import Header from 'components/Header';
import Button from 'components/Button';
import Input from 'components/Input';
import { otpCode } from 'constants/schemas';
import { BLOCKING_NAV_MESSAGE } from 'constants/common';
import { NAVIGATION_URL } from 'constants/routerUrl';
import { goBack, navigateTo, blockNavigation } from 'utils/browser';
import { verifyCodeProps } from '../commonProps';
import s from './VerifyCode.css';

class VerifyCode extends React.Component {
  static propTypes = {
    errors: objectOf(string),
    values: objectOf(any).isRequired,
    handleSubmit: func.isRequired,
    handleChange: func.isRequired,
    isValid: bool.isRequired,
    touched: objectOf(bool).isRequired,
    setFieldTouched: func.isRequired,
    clearOTPStatus: func.isRequired,
  };

  static defaultProps = {
    errors: {},
  };

  state = {
    encryptPhone: null,
    verifyingOTPStatus: null,
  };

  static getDerivedStateFromProps(props, state) {
    const { phoneNumber, encryptPhone, verifyingOTPStatus } = props;
    const {
      phoneNumber: cachedPhoneNumber,
      verifyingOTPStatus: cachedVerifyingOTPStatus,
      encryptPhone: cachedEncryptPhone,
    } = state;

    if (
      phoneNumber !== cachedPhoneNumber ||
      verifyingOTPStatus !== cachedVerifyingOTPStatus ||
      encryptPhone !== cachedEncryptPhone
    ) {
      return {
        phoneNumber,
        verifyingOTPStatus,
        encryptPhone,
      };
    }

    return null;
  }

  componentDidMount() {
    this.unblockNavigation = blockNavigation(BLOCKING_NAV_MESSAGE);
  }

  componentDidUpdate(prevProps) {
    const { verifyingOTPStatus } = prevProps;
    const { verifyingOTPStatus: cachedVerifyingOTPStatus } = this.state;
    if (
      cachedVerifyingOTPStatus &&
      cachedVerifyingOTPStatus !== verifyingOTPStatus
    ) {
      navigateTo(NAVIGATION_URL.TABS.HOME);
    }
  }

  componentWillUnmount() {
    this.unblockNavigation();
  }

  unblockNavigation = null;

  handleChangePhoneNumber = () => {
    const { clearOTPStatus } = this.props;
    goBack();
    clearOTPStatus();
  };

  render() {
    const {
      values,
      handleChange,
      handleSubmit,
      errors,
      isValid,
      touched,
      setFieldTouched,
    } = this.props;
    const { encryptPhone } = this.state;
    const submittingCode = get(values, 'verifyCode');

    return (
      <div className={s.container}>
        <Header
          title="Xác nhận mã kích hoạt"
          iconLeft
          onClickLeft={this.handleChangePhoneNumber}
        />
        <div className={s.textSMS}>
          <p>
            Quý khách vui lòng nhập mã xác thực được gởi đến số điện thoại{' '}
            <span>{encryptPhone}</span>
          </p>
        </div>
        <div className={s.verifyCodeWrapper}>
          <form onSubmit={handleSubmit}>
            <Input
              name="verifyCode"
              type="tel"
              placeholder="Mã xác thực"
              onChange={handleChange}
              value={submittingCode || ''}
              className={s.verifyCode}
              errors={errors}
              touched={touched}
              onTouch={setFieldTouched}
            />
            <Button label="Xác thực" type="submit" disabled={!isValid} />
          </form>
        </div>
      </div>
    );
  }
}

const enhancers = [
  withFormik({
    isInitialValid: false,
    handleSubmit: (
      { verifyCode },
      { props: { dispatchVerifyOTPCode, countryCode, phoneNumber } },
    ) => {
      dispatchVerifyOTPCode(countryCode, phoneNumber, verifyCode);
    },
    validationSchema: otpCode,
  }),
  withStyles(s),
];
export default connect(
  verifyCodeProps.mapStateToProps,
  verifyCodeProps.mapDispatchToProps,
)(compose(...enhancers)(VerifyCode));

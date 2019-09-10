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
import {
  BLOCKING_OTP_MESSAGE,
  BLOCKING_STORE_MESSAGE,
  LS_CREATING_STORE,
} from 'constants/common';
import { ROUTER_URL } from 'constants/routerUrl';
import { navigateTo, blockNavigation, getLocationState } from 'utils/browser';
import { verifyCodeProps } from '../commonProps';
import s from './VerifyOTP.css';

class VerifyOTP extends React.Component {
  static propTypes = {
    errors: objectOf(string),
    values: objectOf(any).isRequired,
    handleSubmit: func.isRequired,
    handleChange: func.isRequired,
    isValid: bool.isRequired,
    touched: objectOf(bool).isRequired,
    setFieldTouched: func.isRequired,
    clearOTPStatus: func.isRequired,
    dispatchCreatingStore: func.isRequired,
  };

  static defaultProps = {
    errors: {},
  };

  state = {
    encryptPhone: null,
    verifyingOTPStatus: null,
    creatingStoreStatus: null,
    creatingStoreProgress: null,
    accessToken: null,
  };

  static getDerivedStateFromProps(props, state) {
    const {
      phoneNumber,
      encryptPhone,
      verifyingOTPStatus,
      creatingStoreStatus,
      creatingStoreProgress,
      accessToken,
    } = props;
    const {
      phoneNumber: cachedPhoneNumber,
      verifyingOTPStatus: cachedVerifyingOTPStatus,
      encryptPhone: cachedEncryptPhone,
      creatingStoreStatus: cachedCreatingStoreStatus,
      creatingStoreProgress: cachedCreatingStoreProgress,
      accessToken: cachedAccessToken,
    } = state;

    if (
      phoneNumber !== cachedPhoneNumber ||
      verifyingOTPStatus !== cachedVerifyingOTPStatus ||
      encryptPhone !== cachedEncryptPhone ||
      creatingStoreStatus !== cachedCreatingStoreStatus ||
      creatingStoreProgress !== cachedCreatingStoreProgress ||
      accessToken !== cachedAccessToken
    ) {
      return {
        phoneNumber,
        verifyingOTPStatus,
        encryptPhone,
        creatingStoreStatus,
        creatingStoreProgress,
        accessToken,
      };
    }

    return null;
  }

  componentDidMount() {
    const warning = getLocationState(LS_CREATING_STORE)
      ? BLOCKING_STORE_MESSAGE
      : BLOCKING_OTP_MESSAGE;
    this.unblockNavigation = blockNavigation(warning);
  }

  componentDidUpdate(prevProps) {
    const { verifyingOTPStatus, storeInfo, dispatchCreatingStore } = prevProps;
    const {
      verifyingOTPStatus: cachedVerifyingOTPStatus,
      creatingStoreStatus,
      creatingStoreProgress,
      accessToken,
    } = this.state;
    const isCreatingStore = getLocationState(LS_CREATING_STORE);

    if (accessToken) {
      this.unblockNavigation();
      navigateTo(ROUTER_URL.AUTH.SHOPS);
    }
    if (
      cachedVerifyingOTPStatus &&
      cachedVerifyingOTPStatus !== verifyingOTPStatus
    ) {
      if (
        isCreatingStore &&
        accessToken &&
        Object.is(creatingStoreStatus, null) &&
        Object.is(creatingStoreProgress, null)
      ) {
        dispatchCreatingStore(storeInfo, accessToken);
      }
    }
  }

  componentWillUnmount() {
    const { clearOTPStatus } = this.props;
    clearOTPStatus();
    this.unblockNavigation();
  }

  unblockNavigation = null;

  handleResendOTP = () => {
    this.unblockNavigation();
    navigateTo(ROUTER_URL.AUTH.ACTIVATION);
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
      <>
        <div className={s.textSMS}>
          <p>
            Vui lòng kiểm tra và nhập mã xác thực được gởi đến số điện thoại{' '}
            <span>{encryptPhone}</span>
          </p>
        </div>
        <div className={s.verifyCodeWrapper}>
          <form onSubmit={handleSubmit}>
            <Input
              name="verifyCode"
              type="tel"
              placeholder="Mã kích hoạt"
              onChange={handleChange}
              value={submittingCode || ''}
              className={s.verifyCode}
              errors={errors}
              touched={touched}
              onTouch={setFieldTouched}
            />
            <Button label="Xác thực" type="submit" disabled={!isValid} />
          </form>
          <div className={s.resendOTP} onClick={this.handleResendOTP}>
            Gởi lại mã OTP
          </div>
        </div>
      </>
    );
  }
}

const enhancers = [
  withFormik({
    isInitialValid: false,
    mapPropsToValues: () => ({
      verifyCode: '',
    }),
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
)(compose(...enhancers)(VerifyOTP));

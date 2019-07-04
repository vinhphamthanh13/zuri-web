import React from 'react';
import OtpCountDown from 'components/common/OTPCountDown';
import { Button, NumberInput } from 'homecredit-ui';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { compose } from 'redux';
import { withFormik } from 'formik';
import { getValidationSchema } from 'utils/validations';
import isEmpty from 'lodash/isEmpty';
import { func, objectOf, any } from 'prop-types';
import noop from 'lodash/noop';

import s from './OTP.css';
import {
  PHONE_PALCEHOLDER,
  MAX_RETRY,
  SECOND_COUNTDOWN,
  MAX_TIME_RESEND_OTP,
} from './constant';

const name = 'otp';

class OTP extends React.PureComponent {
  state = {
    isOTPExpire: false,
    sendOTPCount: 1,
    retryVerify: 0,
  };

  handleTimeoutOTP = () => {
    this.setState({
      isOTPExpire: true,
    });
  };

  /**
   * @param {Boolean} isOverRetry
   */
  handleResendOTP = isOverRetry => {
    const { sendOTPCount } = this.state;
    const { overTry, reSendOTP } = this.props;
    const resendRemain =
      MAX_TIME_RESEND_OTP - sendOTPCount > 1
        ? `0${MAX_TIME_RESEND_OTP - sendOTPCount - 1}`
        : '0';

    if (isOverRetry) {
      overTry();
      return;
    }

    this.setState(() => ({
      isOTPExpire: false,
      sendOTPCount: sendOTPCount + 1,
      retryVerify: 0,
    }));

    reSendOTP(resendRemain);
  };

  handleVerifyOTP = async () => {
    const { retryVerify } = this.state;
    const {
      setFieldTouched,
      validateForm,
      registerDevice,
      values,
    } = this.props;

    setFieldTouched(name);
    const erros = await validateForm();
    if (isEmpty(erros)) {
      registerDevice(values[name], MAX_RETRY - (retryVerify + 1));

      this.setState({
        retryVerify: retryVerify + 1,
      });
    }
  };

  render() {
    const { isOTPExpire } = this.state;
    const { handleBlur, values, touched, errors, setFieldValue } = this.props;

    return (
      <div>
        <div className={s.verifyInput}>
          <NumberInput
            disabled={isOTPExpire}
            onChange={setFieldValue}
            onBlur={handleBlur}
            value={values[name]}
            label={PHONE_PALCEHOLDER}
            name={name}
            touched={touched[name]}
            msgPosition="below"
            errorMsg={errors[name]}
            maxLength={8}
          />
        </div>

        <OtpCountDown
          start={!isOTPExpire}
          disabled={isOTPExpire}
          setOTPTimeOut={this.handleTimeoutOTP}
          resendOTP={this.handleResendOTP}
          seconds={SECOND_COUNTDOWN}
          retry={MAX_TIME_RESEND_OTP}
        />

        <Button className={s.btnSendOTP} onClick={this.handleVerifyOTP}>
          Đăng ký thiết bị
        </Button>
      </div>
    );
  }
}

OTP.propTypes = {
  setFieldValue: noop,
  handleBlur: noop,
  values: objectOf(any),
  touched: objectOf(any),
  errors: objectOf(any),
  setFieldTouched: func,
  validateForm: func,
  registerDevice: func,
  overTry: func,
  reSendOTP: func,
};

OTP.defaultProps = {
  setFieldValue: noop,
  handleBlur: noop,
  values: {},
  touched: {},
  errors: {},
  setFieldTouched: noop,
  validateForm: noop,
  registerDevice: noop,
  overTry: noop,
  reSendOTP: noop,
};

const enchane = compose(
  withFormik({
    mapPropsToValues: () => ({ [name]: '' }),
    validationSchema: getValidationSchema([name]),
  }),
  withStyles(s),
);

export default enchane(OTP);

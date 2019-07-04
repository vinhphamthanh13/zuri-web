import React from 'react';
import { compose } from 'redux';
import { connect } from 'react-redux';
import {
  clearUserInfo,
  setLoaderStatus,
  alert,
  registerDevice,
  unRegisterDevice,
  getSAInfo,
  updateUserInfo,
} from 'actions/saRegister';
import { verifyOTP, generateOTP } from 'actions/clz';
import { SA_ALERT } from 'constants/saRegister';
import noop from 'lodash/noop';
import get from 'lodash/get';
import { func, shape, string } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import { isTokenAlive } from '../common/ultis';
import {
  MESSAGE_OVER_RETRY,
  MESSAGE_VERIFY_FAIL,
  MATCHED,
  MESSAGE_RESEND_REMAIN,
  MESSAGE_RETRY_OTP,
  MESSAGE_ALREADY_REGISTER,
  NUMBER_REMAIN,
  MESSAGE_OTP_WRONG,
  MESSAGE_OUT_OF_TIME,
} from './constant';
import history from '../../../history';
import s from './Home.css';
import PhoneInput from './PhoneInput';
import OTP from './OTP';
import Finished from './Finished';

class Home extends React.PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      verifyId: '',
      phoneNumber: '',
      token: '',
    };
  }

  async componentDidMount() {
    const {
      clearUserInfoAction,
      getSAInfoAction,
      setLoaderAction,
    } = this.props;

    // If token is not alive clear localstorage, and push to /login
    if (!isTokenAlive()) {
      clearUserInfoAction();
      history.push('/sa-register/login');
      return;
    }

    // Check if user is registered
    setLoaderAction(true);
    await getSAInfoAction();
    setLoaderAction(false);
  }

  handlePhoneNumberChange = e => {
    this.setState({
      phoneNumber: e.target.value,
    });
  };

  /**
   * Handle send OTP with number
   */
  handleSendOTP = async phoneNumber => {
    const { setLoaderAction } = this.props;

    setLoaderAction(true);
    const result = await generateOTP(phoneNumber);
    setLoaderAction(false);

    return result;
  };

  /**
   * Handle when phone input submit
   */
  handlePhoneInputSubmit = async phoneNumber => {
    const result = await this.handleSendOTP(phoneNumber);
    this.setState(() => ({ phoneNumber, verifyId: result.verificationID }));
  };

  resetState = () => {
    this.setState(() => ({
      verifyId: '',
      phoneNumber: '',
      token: '',
    }));
  };

  /**
   * Handle OTP over retry
   */
  handleOTPOverRetry = () => {
    const { alertAction } = this.props;
    this.resetState();

    alertAction({
      title: SA_ALERT.info.title,
      message: MESSAGE_OVER_RETRY,
    });
  };

  /**
   * Handle OTP retry, show alert and call API
   */
  handleOTPRetry = resendRemain => {
    const { alertAction } = this.props;

    alertAction({
      title: SA_ALERT.info.title,
      message: (
        <p>
          {MESSAGE_RETRY_OTP[0]}{' '}
          <span className={s.numberOfRetry}>{resendRemain}</span>{' '}
          {MESSAGE_RETRY_OTP[1]}
        </p>
      ),
    });
  };

  /**
   * Handle verified OTP,
   * check if retryleft is
   */
  handleVerifyOTP = async (otp, retryLeft) => {
    const { verifyId, phoneNumber } = this.state;
    const { alertAction } = this.props;

    try {
      const response = await verifyOTP(phoneNumber, verifyId, otp);
      const isMatched = get(response, 'data.status') === MATCHED;

      if (isMatched) {
        const token = get(response, 'token.token');
        return token;
      }

      if (retryLeft) {
        alertAction({
          title: SA_ALERT.error.title,
          message: (
            <>
              <p>{MESSAGE_RESEND_REMAIN}</p>
              <p>
                {MESSAGE_OTP_WRONG.resend}{' '}
                <span className={s.numberOfRetry}>
                  {NUMBER_REMAIN(retryLeft)}
                </span>{' '}
                {MESSAGE_OTP_WRONG.times}
              </p>
            </>
          ),
        });
      }

      if (!retryLeft) {
        alertAction({
          title: SA_ALERT.error.title,
          message: (
            <>
              <p>
                {MESSAGE_OUT_OF_TIME.overtry}{' '}
                <span className={s.numberOfRetry}>
                  {MESSAGE_OUT_OF_TIME.maxTime}
                </span>{' '}
                {MESSAGE_OUT_OF_TIME.times}
              </p>
              <p>{MESSAGE_OVER_RETRY}</p>
            </>
          ),
        });

        this.resetState();
      }

      return '';
    } catch (err) {
      alertAction({
        title: SA_ALERT.error.title,
        message: MESSAGE_VERIFY_FAIL,
      });

      return '';
    }
  };

  /**
   * Handle register device
   */
  registerDevice = async (otp, retryLeft) => {
    const {
      setLoaderAction,
      registerDeviceAction,
      auth,
      getSAInfoAction,
      alertAction,
    } = this.props;
    const { phoneNumber, verifyId } = this.state;
    const sessionID = document
      .getElementById('sessionID')
      .getAttribute('value');

    setLoaderAction(true);
    // Verify OTP
    const token = await this.handleVerifyOTP(otp, retryLeft);
    if (!token) {
      setLoaderAction(false);
      return;
    }

    const info = await getSAInfoAction();

    if (get(info, 'createAt')) {
      alertAction({
        title: SA_ALERT.error.title,
        message: MESSAGE_ALREADY_REGISTER,
      });
      setLoaderAction(false);
      return;
    }

    try {
      await registerDeviceAction({
        phoneNumber,
        retailAgent: auth.username,
        sessionID,
        verificationID: verifyId,
      });
      this.setState({ token });
    } catch (err) {
      this.resetState();
    }

    setLoaderAction(false);
  };

  handleUnRegister = async () => {
    const { unRegisterDeviceAction, setLoaderAction } = this.props;

    setLoaderAction(true);
    await unRegisterDeviceAction();
    this.resetState();
    setLoaderAction(false);
  };

  /**
   * Show step get OTP for register device
   */
  renderStepRegister = () => {
    const { verifyId, token, phoneNumber } = this.state;
    const { createAt } = this.props.auth;

    return (
      <React.Fragment>
        {!token && (
          <p className={s.welcome}>
            Chào mừng bạn đến với hệ thống đăng ký thiết bị của BOCVN.
          </p>
        )}

        {!verifyId && <PhoneInput sendOTP={this.handlePhoneInputSubmit} />}

        {verifyId &&
          !createAt && (
            <OTP
              registerDevice={this.registerDevice}
              reSendOTP={this.handleOTPRetry}
              overTry={this.handleOTPOverRetry}
              phoneNumber={phoneNumber}
            />
          )}
      </React.Fragment>
    );
  };

  render() {
    const { createAt } = this.props.auth;

    return (
      <div className={s.container}>
        {!createAt && this.renderStepRegister()}

        {createAt && (
          <Finished createAt={createAt} unRegister={this.handleUnRegister} />
        )}
      </div>
    );
  }
}

Home.defaultProps = {
  clearUserInfoAction: noop,
  setLoaderAction: noop,
  alertAction: noop,
  registerDeviceAction: noop,
  auth: shape({
    createAt: string,
    username: string,
  }),
};

Home.propTypes = {
  clearUserInfoAction: func,
  setLoaderAction: func,
  alertAction: func,
  registerDeviceAction: func,
  auth: shape({
    createAt: '',
    username: '',
  }),
};

const mapDispatchToProps = dispatch => ({
  clearUserInfoAction: () => dispatch(clearUserInfo()),
  setLoaderAction: status => dispatch(setLoaderStatus(status)),
  alertAction: ({ title, message }) => dispatch(alert({ title, message })),
  /**
   * @param {Object} deviceInfo { phoneNumber, retailAgent, sessionID, verificationID }
   */
  registerDeviceAction: deviceInfo => dispatch(registerDevice(deviceInfo)),
  getSAInfoAction: () => dispatch(getSAInfo()),
  updateUserInfoAction: userInfo => dispatch(updateUserInfo(userInfo)),
  unRegisterDeviceAction: () => dispatch(unRegisterDevice()),
});

const mapStateToProps = ({ saRegister }) => ({ auth: saRegister.auth });

const enchane = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withStyles(s),
);

export default enchane(Home);

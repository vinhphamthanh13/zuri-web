import { ROUTER_URL } from 'constants/routerUrl';
import React, { Component } from 'react';
import { func, objectOf, any, bool, string } from 'prop-types';
import { connect } from 'react-redux';
import { compose } from 'redux';
import Modal from 'components/Modal';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Button from 'components/Button';
import Input from 'components/Input';
import { withFormik } from 'formik';
import { creatingShop } from 'constants/schemas';
import {
  REGISTER,
  INIT_USER,
  BLOCKING_STORE_MESSAGE,
  LS_CREATING_STORE,
} from 'constants/common';
import { blockNavigation, navigateTo } from 'utils/browser';
import BocGreet from 'assets/images/boc_greeting.png';
import { activationProps } from '../commonProps';
import s from './RegisterShop.css';

class RegisterShop extends Component {
  static propTypes = {
    handleChange: func.isRequired,
    values: objectOf(any).isRequired,
    errors: objectOf(any).isRequired,
    isValid: bool.isRequired,
    touched: objectOf(bool).isRequired,
    setFieldTouched: func.isRequired,
    dispatchExistingUserAction: func.isRequired,
    dispatchCreatingUserAction: func.isRequired,
    creatingUser: bool,
    phoneNumber: string.isRequired,
    handleSubmit: func.isRequired,
    dispatchCleanUpAuthentication: func.isRequired,
  };

  static defaultProps = {
    creatingUser: false,
  };

  state = {
    creatingUserPopup: true,
  };

  static getDerivedStateFromProps(props, state) {
    const { sendingOTPStatus } = props;
    const { sendingOTPStatus: cachedSendingOTPStatus } = state;

    if (sendingOTPStatus !== cachedSendingOTPStatus) {
      return {
        sendingOTPStatus,
      };
    }

    return null;
  }

  componentDidMount() {
    const { dispatchCleanUpAuthentication } = this.props;
    dispatchCleanUpAuthentication();
    this.unblockNavigation = blockNavigation(BLOCKING_STORE_MESSAGE);
  }

  componentDidUpdate(prevProps) {
    const { sendingOTPStatus } = prevProps;
    const { sendingOTPStatus: cachedSendingOTPStatus } = this.state;

    if (cachedSendingOTPStatus && cachedSendingOTPStatus !== sendingOTPStatus) {
      this.unblockNavigation();
      navigateTo(ROUTER_URL.AUTH.VERIFYING_OTP, { [LS_CREATING_STORE]: true });
    }
  }

  componentWillUnmount() {
    const {
      dispatchExistingUserAction,
      dispatchCreatingUserAction,
    } = this.props;
    dispatchExistingUserAction(INIT_USER);
    dispatchCreatingUserAction(false);
    this.unblockNavigation();
  }

  unblockNavigation = null;

  createForm = () => {
    const {
      handleChange,
      errors,
      values,
      touched,
      setFieldTouched,
    } = this.props;
    return Object.keys(REGISTER).map(input => (
      <Input
        type={REGISTER[input].TYPE}
        key={REGISTER[input].VALUE}
        label={REGISTER[input].LABEL}
        name={REGISTER[input].VALUE}
        onChange={handleChange}
        errors={errors}
        className={
          REGISTER[input].TYPE === 'checkbox' ? s.policiesAndTerms : ''
        }
        value={values[REGISTER[input].VALUE]}
        placeholder={REGISTER[input].PLACEHOLDER}
        onTouch={setFieldTouched}
        touched={touched}
      />
    ));
  };

  handleCloseCreatingUserPopup = () =>
    this.setState({ creatingUserPopup: false });

  render() {
    const { isValid, creatingUser, phoneNumber, handleSubmit } = this.props;
    const { creatingUserPopup } = this.state;
    const popUpCongratulation = creatingUser && creatingUserPopup;

    return (
      <>
        {popUpCongratulation && (
          <Modal
            title="Tạo tài khoản thành công!"
            message={`Bạn đã tạo tài khoản thành công trên hệ thống BOCVN với số điện thoại ${phoneNumber}. Hãy tạo cửa hàng cho bạn.`}
            successIcon
            onClose={this.handleCloseCreatingUserPopup}
          />
        )}
        <div className={s.greetingLogo}>
          <img src={BocGreet} alt="Boc Greeting" width="100%" />
        </div>
        <div className={s.register}>
          <form onSubmit={handleSubmit} className={s.creatingForm}>
            {this.createForm()}
            <Button
              type="submit"
              label="Tiếp theo"
              disabled={!isValid}
              className={s.button}
            />
          </form>
        </div>
        <div className={s.readingPolicies}>
          <a href="#">Chính sách và điều khoản BOCVN</a>
        </div>
      </>
    );
  }
}

const enhancers = [
  withFormik({
    mapPropsToValues: () => ({
      userName: '',
      shopName: '',
      managerPhone: '',
      shopAddress: '01 đường số 34, Bình Trị Đông B, Bình Tân, TP.HCM',
      businessType: '',
      categoryType: '',
      userEmail: '',
      policiesAndTerms: false,
    }),
    validateOnBlur: true,
    validationSchema: creatingShop,
    handleSubmit: (
      values,
      {
        props: {
          countryCode,
          phoneNumber,
          dispatchSendOTP,
          dispatchCreatingStoreInfo,
        },
      },
    ) => {
      if (countryCode && phoneNumber) dispatchSendOTP(countryCode, phoneNumber);
      dispatchCreatingStoreInfo({ ...values, phoneNumber });
    },
  }),
  withStyles(s),
];

export default connect(
  activationProps.mapStateToProps,
  activationProps.mapDispatchToProps,
)(compose(...enhancers)(RegisterShop));

import React, { Component } from 'react';
import { func, objectOf, any, bool, string } from 'prop-types';
import { connect } from 'react-redux';
import { compose } from 'redux';
import Header from 'components/Header';
import Modal from 'components/Modal';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Button from 'components/Button';
import Input from 'components/Input';
import { withFormik } from 'formik';
import { register } from 'constants/schemas';
import { REGISTER, INIT_USER, BLOCKING_NAV_MESSAGE } from 'constants/common';
import { goBack, blockNavigation, navigateTo } from 'utils/browser';
import BocGreet from 'assets/images/boc_greeting.png';
import { creatingStoreProps } from '../commonProps';
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
  };

  static defaultProps = {
    creatingUser: false,
  };

  state = {
    creatingUserPopup: true,
  };

  componentDidMount() {
    this.unblockNavigation = blockNavigation(BLOCKING_NAV_MESSAGE);
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

  handleActivation = () => {
    const {
      values: { policiesAndTerms },
    } = this.props;
    navigateTo('/activation', { register: policiesAndTerms });
  };

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
    const { isValid, creatingUser, phoneNumber } = this.props;
    const { creatingUserPopup } = this.state;
    const popUpCongratulation = creatingUser && creatingUserPopup;

    return (
      <>
        {popUpCongratulation && (
          <Modal
            title="Tạo tài khoản thành công!"
            message={`Bạn đã tạo tài khoản thành công với số điện thoại ${phoneNumber}. Hãy tạo cửa hàng cho bạn.`}
            successIcon
            callback={this.handleCloseCreatingUserPopup}
          />
        )}
        <div className={s.container}>
          <Header title="Tạo cửa hàng" iconLeft onClickLeft={goBack} />
          <div className={s.greetingLogo}>
            <img src={BocGreet} alt="Boc Greeting" width="100%" />
          </div>
          <div className={s.register}>
            <form>{this.createForm()}</form>
            <Button
              onClick={this.handleActivation}
              label="Tiếp theo"
              disabled={!isValid}
              className={s.button}
            />
          </div>
        </div>
      </>
    );
  }
}

export default compose(
  withFormik({
    mapPropsToValues: () => ({
      userName: 'BOC',
      shopName: 'BOCVN',
      shopAddress: '01 đường số 34, Bình Trị Đông B, Bình Tân, TP.HCM',
      businessType: 'Quán Cao Cấp',
      categoryType: 'Coffee',
      userEmail: 'bocvn2020@gmail.com',
      policiesAndTerms: false,
    }),
    validateOnBlur: true,
    validationSchema: register,
  }),
  connect(
    creatingStoreProps.mapStateToProps,
    creatingStoreProps.mapDispatchToProps,
  ),
  withStyles(s),
)(RegisterShop);

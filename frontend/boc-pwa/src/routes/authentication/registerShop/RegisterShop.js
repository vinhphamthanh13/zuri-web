import React, { Component } from 'react';
import { func, objectOf, any, bool } from 'prop-types';
import { connect } from 'react-redux';
import { compose } from 'redux';
import Header from 'components/Header';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Button from 'components/Button';
import Input from 'components/Input';
import { withFormik } from 'formik';
import { register } from 'constants/schemas';
import { REGISTER, INIT_USER } from 'constants/common';
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
  };

  componentDidMount() {
    this.unblockNavigation = blockNavigation(
      'Bạn có muốn thoát khỏi đăng ký cửa hàng? Dữ liệu chưa lưu sẽ bị xóa khi thoát chương trình!',
    );
  }

  componentWillUnmount() {
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

  handleGoBack = () => {
    const {
      dispatchExistingUserAction,
      dispatchCreatingUserAction,
    } = this.props;
    dispatchExistingUserAction(INIT_USER);
    dispatchCreatingUserAction(false);
    goBack(true, 'Thoát khỏi chương trình!');
  };

  render() {
    const { isValid } = this.props;

    return (
      <div className={s.container}>
        <Header title="Tạo cửa hàng" iconLeft onClickLeft={this.handleGoBack} />
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
        <a href="#" className={s.readingPolicies}>
          Chính sách và điều khoản của BOCVN
        </a>
      </div>
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
    null,
    creatingStoreProps.mapDispatchToProps,
  ),
  withStyles(s),
)(RegisterShop);

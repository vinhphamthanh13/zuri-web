import React, { Component } from 'react';
import { func, objectOf, any, bool } from 'prop-types';
import { connect } from 'react-redux';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Header from 'components/Header';
import Button from 'components/Button';
import Input from 'components/Input';
import { withFormik } from 'formik';
import { register } from 'constants/schemas';
import { REGISTER } from 'constants/common';
import BocGreet from 'assets/images/boc_greeting.png';
import history from '../../../history';
import s from './Register.css';

class Register extends Component {
  static propTypes = {
    handleChange: func.isRequired,
    handleSubmit: func.isRequired,
    values: objectOf(any).isRequired,
    errors: objectOf(any).isRequired,
    isValid: bool.isRequired,
    touched: objectOf(bool).isRequired,
    setFieldTouched: func.isRequired,
  };

  handleActivation = () => {
    history.push('/activation');
  };
  handleRedirectLogin = () => {
    history.push('/activation');
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
        value={values[REGISTER[input].VALUE]}
        placeholder={REGISTER[input].PLACEHOLDER}
        onTouch={setFieldTouched}
        touched={touched}
      />
    ));
  };

  render() {
    const { isValid, handleSubmit } = this.props;
    return (
      <div className={s.container}>
        <Header title="Tạo cửa hàng" />
        <div className={s.greetingLogo}>
          <img src={BocGreet} alt="Boc Greeting" width="100%" />
        </div>
        <div className={s.register}>
          <form>{this.createForm()}</form>
          <Button
            onClick={handleSubmit}
            label="Tiếp theo"
            disabled={!isValid}
          />
        </div>
        <div className={s.login}>
          Hoặc đã có tài khoản?{' '}
          <span onClick={this.handleRedirectLogin}>Đăng nhập</span>
        </div>
      </div>
    );
  }
}

export default compose(
  withFormik({
    mapPropsToValues: () => ({
      userName: 'BOC',
      shopName: 'BOCVN',
      phoneNumber: '0936388480',
      shopAddress: '01, đường số 1, Bình Trị Đông B,',
      businessType: 'Quán Cao Cấp',
      categoryType: 'Coffee',
      userEmail: 'bocvn2020@gmail.com',
    }),
    validationSchema: register,
    isInitialValid: true,
    handleSubmit: values => {
      console.log('values on submit', values);
    },
  }),
  connect(null),
  withStyles(s),
)(Register);

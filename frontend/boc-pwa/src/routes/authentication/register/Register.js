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
    values: objectOf(any).isRequired,
    errors: objectOf(any).isRequired,
    isValid: bool.isRequired,
    touched: objectOf(bool).isRequired,
    setFieldTouched: func.isRequired,
  };

  handleActivation = () => {
    const {
      values: { policiesAndTerms },
    } = this.props;
    history.push('/activation', { register: policiesAndTerms });
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

  render() {
    const {
      isValid,
      values: { policiesAndTerms },
    } = this.props;

    return (
      <div className={s.container}>
        <Header title="Tạo cửa hàng" />
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
        {!policiesAndTerms && (
          <div className={s.login}>
            Hoặc đã có tài khoản?{' '}
            <span onClick={this.handleActivation}>Đăng nhập</span>
          </div>
        )}
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
  connect(null),
  withStyles(s),
)(Register);

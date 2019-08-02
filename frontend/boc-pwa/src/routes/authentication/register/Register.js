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
import { INPUT } from 'constants/common';
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
  };

  handleActivation = () => {
    history.push('/activation');
  };

  createForm = () => {
    const { handleChange, errors, values } = this.props;
    return Object.keys(INPUT).map(input => (
      <Input
        key={INPUT[input].value}
        label={INPUT[input].label}
        name={INPUT[input].value}
        onChange={handleChange}
        errors={errors}
        value={values[INPUT[input].value]}
        placeholder={INPUT[input].placeholder}
      />
    ));
  };

  render() {
    const { isValid, handleSubmit } = this.props;

    return (
      <div className={s.container}>
        <Header title="Tạo tài khoản" />
        <div className={s.greetingLogo}>
          <img src={BocGreet} alt="Boc Greeting" width="100%" />
        </div>
        <div className={s.register}>
          <form onSubmit={handleSubmit}>{this.createForm()}</form>
          <Button
            onClick={this.handleRegister}
            label="Tiếp theo"
            disabled={!isValid}
          />
        </div>
      </div>
    );
  }
}

export default compose(
  withFormik({
    initialValues: {
      userName: '',
      lastName: '',
      phoneNumber: '',
      shopAddress: '',
    },
    validationSchema: register,
    // isInitialValid: true,
    handleSubmit: (values, { isSubmitting }) => {
      console.log('values on submit', values);
    },
  }),
  connect(null),
  withStyles(s),
)(Register);

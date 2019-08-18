import React, { Component } from 'react';
import { func, objectOf, any, bool } from 'prop-types';
import { compose } from 'redux';
import { connect } from 'react-redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Button from 'components/Button';
import Input from 'components/Input';
import { withFormik } from 'formik';
import { registerTax } from 'constants/schemas';
import { TAX } from 'constants/common';
import s from './RegisterTax.css';

class RegisterTax extends Component {
  static propTypes = {
    handleChange: func.isRequired,
    handleSubmit: func.isRequired,
    values: objectOf(any).isRequired,
    errors: objectOf(any).isRequired,
    isValid: bool.isRequired,
  };

  handleRegisterTax = () => {
    console.log('register Tax');
  };

  createForm = () => {
    const { handleChange, errors, values } = this.props;
    return Object.keys(TAX).map(input => (
      <Input
        key={TAX[input].VALUE}
        label={TAX[input].LABEL}
        name={TAX[input].VALUE}
        onChange={handleChange}
        errors={errors}
        value={values[TAX[input].VALUE]}
        placeholder={TAX[input].PLACEHOLDER}
      />
    ));
  };

  render() {
    const { isValid, handleSubmit } = this.props;

    return (
      <div className={s.container}>
        <div className={s.register}>
          <div className={s.formTitle}>Thông tin đăng ký</div>
          <form onSubmit={handleSubmit}>{this.createForm()}</form>
          <Button
            onClick={this.handleRegisterTax}
            label="Lưu"
            disabled={!isValid}
          />
        </div>
      </div>
    );
  }
}

const mapStateToProps = state => ({});

export default compose(
  withFormik({
    initialValues: {
      coName: '',
      coAddress: '',
      branchName: '',
      taxNumber: '',
      registerNumber: '',
    },
    validationSchema: registerTax,
    // isInitialValid: true,
    handleSubmit: (values, { isSubmitting }) => {
      console.log('values on submit', values);
    },
  }),
  connect(mapStateToProps),
  withStyles(s),
)(RegisterTax);

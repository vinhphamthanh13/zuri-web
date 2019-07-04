import React from 'react';
import { NumberInput, Button } from 'homecredit-ui';
import { compose } from 'redux';
import { withFormik } from 'formik';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { getValidationSchema } from 'utils/validations';
import isEmpty from 'lodash/isEmpty';
import { func, string, shape } from 'prop-types';
import noop from 'lodash/noop';

import { INPUT_PLACEHOLDER } from './constant';
import s from './PhoneInput.css';

const name = 'phoneNumber';

function PhoneInput(props) {
  const {
    values,
    errors,
    touched,
    setFieldValue,
    sendOTP,
    validateForm,
    handleBlur,
    setFieldTouched,
  } = props;

  async function handleSendOTP() {
    setFieldTouched(name);
    const erros = await validateForm();
    if (isEmpty(erros)) sendOTP(values[name]);
  }

  return (
    <div>
      <NumberInput
        onChange={setFieldValue}
        onBlur={handleBlur}
        value={values[name]}
        label={INPUT_PLACEHOLDER}
        name={name}
        touched={touched[name]}
        msgPosition="below"
        errorMsg={errors[name]}
        maxLength={10}
      />
      <Button className={s.btnSendOTP} onClick={handleSendOTP}>
        Gửi OTP
      </Button>
    </div>
  );
}

PhoneInput.defaultProps = {
  values: shape({}),
  touched: shape({}),
  errors: shape({}),
  setFieldValue: noop,
  sendOTP: noop,
  validateForm: noop,
  handleBlur: noop,
  setFieldTouched: noop,
};

PhoneInput.propTypes = {
  values: shape({
    [name]: string,
  }),
  touched: shape({
    [name]: string,
  }),
  errors: shape({
    [name]: string,
  }),
  setFieldValue: func,
  sendOTP: func,
  validateForm: func,
  handleBlur: func,
  setFieldTouched: func,
};

const enchane = compose(
  withFormik({
    mapPropsToValues: () => ({ [name]: '' }),
    validationSchema: getValidationSchema([name]),
  }),
  withStyles(s),
);

export default enchane(PhoneInput);

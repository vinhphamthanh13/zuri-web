import { REGEXP } from 'constants/common';
import React, { Component } from 'react';
import { func, objectOf, any, bool } from 'prop-types';
import { get } from 'lodash';
import { connect } from 'react-redux';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { withFormik } from 'formik/dist/index';
import Header from 'components/Header';
import Button from 'components/Button';
import Input from 'components/Input';
import { activation } from 'constants/schemas';
import history from '../../../history';
import { mapDispatchToProps } from './commonProps';
import s from './Activation.css';

class Activation extends Component {
  static propTypes = {
    values: objectOf(any).isRequired,
    setPhoneNumber: func.isRequired,
    errors: objectOf(any),
    isValid: bool.isRequired,
    handleChange: func.isRequired,
    handleSubmit: func.isRequired,
    loginPhone: func.isRequired,
  };

  static defaultProps = {
    errors: {},
  };

  state = {
    isSubmitting: false,
    isValidating: null,
  };

  static getDerivedStateFromProps(props, state) {
    const { isSubmitting, isValidating } = props;
    const {
      isSubmitting: cachedIsSubmitting,
      isValidating: cachedIsValidating,
    } = state;
    if (
      isSubmitting !== cachedIsSubmitting ||
      isValidating !== cachedIsValidating
    ) {
      return {
        isSubmitting,
        isValidating,
      };
    }

    return null;
  }

  componentDidUpdate() {
    const { setPhoneNumber, values, isValid, loginPhone } = this.props;
    const { isSubmitting, isValidating } = this.state;
    const phoneNumber = get(values, 'phoneNumber');
    const countryCode = get(values, 'countryCode');

    if (isValid && isSubmitting && isValidating) {
      const registerPhoneNumber = `${countryCode}${phoneNumber.replace(
        /^0(\d+)/,
        '$1',
      )}`;
      const encryptPhone = registerPhoneNumber.replace(
        REGEXP.ENCRYPT_PHONE,
        '$1*****$2',
      );
      setPhoneNumber(encryptPhone);
      // loginPhone(registerPhoneNumber);
    }
  }

  render() {
    const {
      values: { countryCode, phoneNumber },
      handleChange,
      handleSubmit,
      errors,
      isValid,
    } = this.props;

    return (
      <>
        <div className={s.container}>
          <Header title="đăng nhập số điện thoại" />
          <form onSubmit={handleSubmit}>
            <div className={s.inputs}>
              <Input
                name="countryCode"
                value={countryCode}
                onChange={handleChange}
                className={s.countryCode}
                disabled
              />
              <Input
                name="phoneNumber"
                type="tel"
                value={phoneNumber}
                placeholder="Số điện thoại"
                onChange={handleChange}
                className={s.phoneNumber}
                errors={errors}
              />
            </div>
            <Button label="Lấy Mã Xác Nhận" type="submit" disabled={!isValid} />
          </form>
        </div>
      </>
    );
  }
}

export default compose(
  withFormik({
    mapPropsToValues: props => ({
      countryCode: props.code,
      phoneNumber: '',
    }),
    validationSchema: activation,
    handleSubmit: (values, { setSubmitting }) => {
      setSubmitting(true);
      setTimeout(() => {
        setSubmitting(false);
        history.push('/verify-code');
      }, 1200);
    },
  }),
  connect(
    null,
    mapDispatchToProps,
  ),
  withStyles(s),
)(Activation);

/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { func, objectOf, any, bool, string } from 'prop-types';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { get } from 'lodash';
import { withFormik } from 'formik';
import Header from 'components/Header';
import Button from 'components/Button';
import Input from 'components/Input';
import { verifyCode } from 'constants/schemas';
import history from '../../../history';
import { mapStateToProps } from './commonProps';
import s from './VerifyCode.css';

class VerifyCode extends React.Component {
  static propTypes = {
    errors: objectOf(string),
    values: objectOf(any).isRequired,
    handleSubmit: func.isRequired,
    handleChange: func.isRequired,
    isValid: bool.isRequired,
  };

  static defaultProps = {
    errors: {},
  };

  state = {
    phoneNumber: null,
  };

  static getDerivedStateFromProps(props, state) {
    const { phoneNumber } = props;
    const { phoneNumber: cachedPhoneNumber } = state;

    if (phoneNumber !== cachedPhoneNumber) {
      return {
        phoneNumber,
      };
    }

    return null;
  }

  render() {
    const { values, handleChange, handleSubmit, errors, isValid } = this.props;
    const { phoneNumber } = this.state;
    const submittingCode = get(values, 'verifyCode');

    return (
      <div className={s.container}>
        <Header title="Xác nhận mã kích hoạt" />
        <div className={s.textSMS}>
          Quý khách vui lòng nhập mã xác thực được gởi qua SMS đến số đăng ký:{' '}
          {phoneNumber}
        </div>
        <div className={s.verifyCodeWrapper}>
          <form onSubmit={handleSubmit}>
            <Input
              name="verifyCode"
              type="tel"
              placeholder="Mã xác thực"
              onChange={handleChange}
              value={submittingCode || ''}
              className={s.verifyCode}
              errors={errors}
            />
            <Button label="Xác thực" type="submit" disabled={!isValid} />
          </form>
        </div>
      </div>
    );
  }
}

export default compose(
  withFormik({
    isInitialValid: false,
    handleSubmit: (values, { setSubmitting }, props) => {
      const code = get(values, 'verifyCode');
      setSubmitting(true);
      setTimeout(() => {
        setSubmitting(false);
      }, 1000);
    },
    validationSchema: verifyCode,
  }),
  connect(mapStateToProps),
  withStyles(s),
)(VerifyCode);

import React, { PureComponent } from 'react';
import { Input, Button } from 'homecredit-ui';
import { withFormik } from 'formik';
import { compose } from 'redux';
import { connect } from 'react-redux';
import { func, shape, bool, string } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import noop from 'lodash/noop';
import isEmpty from 'lodash/isEmpty';

import { getValidationSchema } from 'utils/validations';
import { login, logout } from 'actions/internal';
import history from '../../../history';
import s from './Login.css';

import {
  LOGIN_TITLE,
  USERNAME_PLACEHOLDER,
  PASSWORD_PLACEHOLDER,
  LOGIN_BUTTON,
} from './constant';

class Login extends PureComponent {
  static propTypes = {
    values: shape({
      username: string,
      password: string,
    }),
    loginAction: func,
    logout: func,
    touched: shape({
      username: bool,
      password: bool,
    }),
    errors: shape({
      username: string,
      password: string,
    }),
    handleChange: func,
    handleBlur: func,
    validateForm: func.isRequired,
    setFieldTouched: func.isRequired,
  };

  static defaultProps = {
    values: {},
    loginAction: noop,
    logout: noop,
    touched: {},
    errors: {},
    handleChange: noop,
    handleBlur: noop,
  };

  componentWillMount() {
    this.props.logout(null);
  }

  handleChange = e => {
    const { handleChange } = this.props;
    handleChange(e);
  };

  handleLogin = () => {
    const { values, loginAction } = this.props;
    const { username, password } = values;
    loginAction(username, password, history);
  };

  handleSubmit = async e => {
    e.preventDefault();
    const { validateForm, setFieldTouched } = this.props;
    const errors = await validateForm();

    if (isEmpty(errors)) {
      this.handleLogin();
    } else {
      Object.keys(errors).forEach(error => setFieldTouched(error));
    }
  };

  render() {
    const { touched, errors, handleBlur } = this.props;
    const { username, password } = this.props.values;

    return (
      <div className={s.container}>
        <div className={s.login}>
          <form onSubmit={this.handleSubmit}>
            <h1 className={s.title}>{LOGIN_TITLE}</h1>
            <Input
              id="username"
              autoFocus
              label={USERNAME_PLACEHOLDER}
              name="username"
              value={username}
              onChange={this.handleChange}
              onBlur={handleBlur}
              touched={errors.username && touched.username}
              errorMsg={errors.username}
            />

            <Input
              id="password"
              label={PASSWORD_PLACEHOLDER}
              value={password}
              onChange={this.handleChange}
              name="password"
              type="password"
              onBlur={handleBlur}
              touched={errors.password && touched.password}
              errorMsg={errors.password}
            />
            <div className={s.groupBtn}>
              <Button onClick={this.handleSubmit}>{LOGIN_BUTTON}</Button>
              <input className={s.submitBtn} type="submit" />
            </div>
          </form>
        </div>
      </div>
    );
  }
}

const enhance = compose(
  connect(
    null,
    { loginAction: login, logout },
  ),
  withFormik({
    mapPropsToValues: () => ({ username: '', password: '' }),
    validationSchema: getValidationSchema(['username', 'password']),
  }),
  withStyles(s),
);

export default enhance(Login);

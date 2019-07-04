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
import { login, clearUserInfo } from 'actions/saRegister';
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
      saUsername: string,
      saPassword: string,
    }),
    loginAction: func,
    touched: shape({
      saUsername: bool,
      saPassword: bool,
    }),
    errors: shape({
      saUsername: string,
      saPassword: string,
    }),
    clearUserInfoAction: func,
    handleChange: func,
    handleBlur: func,
    validateForm: func.isRequired,
    setFieldTouched: func.isRequired,
  };

  static defaultProps = {
    values: {},
    loginAction: noop,
    clearUserInfoAction: noop,
    touched: {},
    errors: {},
    handleChange: noop,
    handleBlur: noop,
  };

  /**
   * clear sa token when user enter route
   */
  componentDidMount = () => {
    this.props.clearUserInfoAction();
  };

  handleChange = e => {
    const { handleChange } = this.props;
    const { name, value } = e.target;

    if (name === 'saUsername' && /[^a-zA-Z0-9]/.test(value)) return;

    handleChange(e);
  };

  handleLogin = () => {
    const { values, loginAction } = this.props;
    const { saUsername, saPassword } = values;
    loginAction(saUsername, saPassword);
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
    const { saUsername, saPassword } = this.props.values;

    return (
      <div className={s.container}>
        <div className={s.login}>
          <form onSubmit={this.handleSubmit}>
            <h2 className={s.title}>{LOGIN_TITLE}</h2>
            <Input
              id="saUsername"
              label={USERNAME_PLACEHOLDER}
              name="saUsername"
              value={saUsername}
              onChange={this.handleChange}
              onBlur={handleBlur}
              touched={errors.saUsername && touched.saUsername}
              errorMsg={errors.saUsername}
              msgPosition="below"
              maxLength={20}
            />

            <Input
              id="saPassword"
              label={PASSWORD_PLACEHOLDER}
              value={saPassword}
              onChange={this.handleChange}
              name="saPassword"
              type="password"
              onBlur={handleBlur}
              touched={errors.saPassword && touched.saPassword}
              errorMsg={errors.saPassword}
              msgPosition="below"
            />
          </form>
          <div className={s.groupBtn}>
            <Button onClick={this.handleSubmit}>{LOGIN_BUTTON}</Button>
          </div>
        </div>
      </div>
    );
  }
}

const enhance = compose(
  connect(
    null,
    dispatch => ({
      loginAction: (username, password) => dispatch(login(username, password)),
      clearUserInfoAction: () => dispatch(clearUserInfo()),
    }),
  ),
  withFormik({
    mapPropsToValues: () => ({ saUsername: '', saPassword: '' }),
    validationSchema: getValidationSchema(['saUsername', 'saPassword']),
  }),
  withStyles(s),
);

export default enhance(Login);

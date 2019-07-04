import { SA_TOKEN, AUTH, MESSAGE, SA_ALERT } from 'constants/saRegister';
import { post } from 'utils/http';
import { URL } from 'constants/api';

import { clearSAToken } from './utils';
import { alert } from './alert';
import { setLoaderStatus } from './loading';
import history from '../../history';

export function loginSuccess(auth) {
  return {
    type: AUTH.SA_LOGIN_SUCCESS,
    auth,
  };
}

export function clearUserInfo() {
  clearSAToken();

  return {
    type: AUTH.SA_LOGOUT_SUCCESS,
  };
}

export function loginFailed() {
  return {
    type: AUTH.SA_LOGIN_FAILED,
  };
}

export const login = (username, password) => async dispatch => {
  const res = await post(URL.SA_LOGIN, { username, password });

  if (res.ok) {
    dispatch(setLoaderStatus(true));

    const auth = await res.json();
    const { token, expiresIn, user } = auth.data;

    // save to localstorage
    localStorage.setItem(
      SA_TOKEN,
      JSON.stringify({
        token,
        expiresIn: new Date(Date.now() + expiresIn).getTime(),
      }),
    );

    dispatch(loginSuccess({ username: user }));
    history.push('/sa-register/dashboard');

    return { username: user };
  }
  dispatch(loginFailed());
  dispatch(
    alert({
      title: SA_ALERT.info.title,
      message: MESSAGE.LOGIN_FAIL,
    }),
  );

  dispatch(setLoaderStatus(false));

  return '';
};

export const updateUserInfo = ({ createAt }) => ({
  type: AUTH.SA_UPDATE_USER,
  auth: { createAt },
});

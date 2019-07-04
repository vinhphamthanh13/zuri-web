import { isEmpty } from 'lodash';

import { AUTH, MESSAGE } from 'constants/internal';
import { fetchConfig } from './fetchConfig';
import { loading, loaded, error, alerts } from './app';

export function loginFailed() {
  return {
    type: AUTH.LOGIN_FAILED,
  };
}

export function loginSuccess(token) {
  return {
    type: AUTH.LOGIN_SUCCESS,
    token,
  };
}

export function logoutSuccess() {
  return {
    type: AUTH.LOGOUT_SUCCESS,
  };
}

export const checkAuth = (isRequiredLogin, history) => dispatch => {
  const authInfo = localStorage.getItem('internalAuth');
  if (isEmpty(authInfo)) {
    isRequiredLogin && history.push('/internal/login');
    return;
  }

  const auth = JSON.parse(authInfo);
  const current = new Date().getTime() / 1000;
  if (!auth || auth.expiresIn < current) {
    isRequiredLogin && history.push('/internal/login');
    return;
  }

  dispatch(loginSuccess(auth));
};

export const logout = history => dispatch => {
  dispatch(logoutSuccess());
  history && history.push('/internal/login');
};

export const login = (username, password, history) => async dispatch => {
  try {
    dispatch(loading());
    const res = await fetch(
      '/api/user/auth',
      fetchConfig({ method: 'POST' }, { username, password }),
    );
    if (res.ok) {
      const auth = await res.json();
      dispatch(loginSuccess(auth));
      history.push('/internal/uploadsale');
    } else {
      dispatch(loginFailed());
      dispatch(alerts.warn(MESSAGE.LOGIN_FAILED_INFO));
    }
  } catch (e) {
    dispatch(error(e, e.message));
  } finally {
    dispatch(loaded());
  }
};

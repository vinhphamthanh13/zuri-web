import { AUTH, DEFAULT_STATE } from 'constants/internal';

export default function auth(state = DEFAULT_STATE.auth, action) {
  const { type, token } = action;
  switch (type) {
    case AUTH.LOGIN_SUCCESS:
      localStorage.setItem('internalAuth', JSON.stringify(token));
      return token;
    case AUTH.LOGOUT_SUCCESS:
      localStorage.removeItem('internalAuth');
      return DEFAULT_STATE.auth;
    default:
      return state;
  }
}

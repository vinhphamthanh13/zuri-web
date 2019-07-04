import { AUTH } from 'constants/saRegister';

const defualtState = {
  username: null,
  createAt: null,
};

export function auth(state = defualtState, action) {
  const { type, auth: userInfo } = action;

  switch (type) {
    case AUTH.SA_LOGIN_SUCCESS: {
      return Object.assign({}, state, userInfo);
    }
    case AUTH.SA_UPDATE_USER: {
      return Object.assign({}, state, userInfo);
    }
    case AUTH.SA_LOGOUT_SUCCESS: {
      return defualtState;
    }
    default: {
      return state;
    }
  }
}

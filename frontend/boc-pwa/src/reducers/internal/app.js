import { APP, APP_ALERT, DEFAULT_STATE } from 'constants/internal';

export function error(state = DEFAULT_STATE.error, action) {
  switch (action.type) {
    case APP.ERROR:
      return {
        errors: action.errors,
        message: action.message,
      };
    default:
      return state;
  }
}

export function loading(state = DEFAULT_STATE.loading, action) {
  switch (action.type) {
    case APP.LOADING:
      return true;
    case APP.LOADED:
      return false;
    default:
      return state;
  }
}

export function alert(state = DEFAULT_STATE.alert, action) {
  const { type, message } = action;
  switch (type) {
    case APP_ALERT.SUCCESS:
      return {
        type: 'success',
        message,
      };
    case APP_ALERT.WARN:
      return {
        type: 'warn',
        message,
      };
    case APP_ALERT.ERROR:
      return {
        type: 'error',
        message,
      };
    case APP_ALERT.CLEAR:
      return {};
    default:
      return state;
  }
}

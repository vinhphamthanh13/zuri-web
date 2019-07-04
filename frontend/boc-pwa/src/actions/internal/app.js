import { APP, APP_ALERT } from 'constants/internal';

export function error(errors, message) {
  return {
    type: APP.ERROR,
    errors,
    message,
  };
}

export function loading() {
  return {
    type: APP.LOADING,
  };
}

export function loaded() {
  return {
    type: APP.LOADED,
  };
}

export const alerts = {
  success(message) {
    return {
      type: APP_ALERT.SUCCESS,
      message,
    };
  },
  warn(message) {
    return {
      type: APP_ALERT.WARN,
      message,
    };
  },
  error(err, message) {
    return {
      type: APP_ALERT.ERROR,
      message,
    };
  },
  clear() {
    return {
      type: APP_ALERT.CLEAR,
    };
  },
};

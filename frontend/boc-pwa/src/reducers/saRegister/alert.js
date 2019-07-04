import { SA_ALERT_MSG, SA_ALERT_CLEAR } from 'constants/saRegister';

const defaultState = {
  title: '',
  message: '',
};

export function alert(state = defaultState, action) {
  const { type, alert: alertAction } = action;

  switch (type) {
    case SA_ALERT_MSG: {
      return Object.assign({}, state, alertAction);
    }
    case SA_ALERT_CLEAR: {
      return defaultState;
    }
    default: {
      return state;
    }
  }
}

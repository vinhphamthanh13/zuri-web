import { SA_ALERT_MSG, SA_ALERT_CLEAR } from 'constants/saRegister';

/**
 * Set msg warning
 * @param {string} title for title of alert
 * @param {string} message include alertType and message
 */
export function alert({ title, message }) {
  return {
    type: SA_ALERT_MSG,
    alert: { title, message },
  };
}

export function clearAlert() {
  return {
    type: SA_ALERT_CLEAR,
  };
}

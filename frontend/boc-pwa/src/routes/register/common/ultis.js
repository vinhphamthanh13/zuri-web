import { SA_TOKEN } from 'constants/saRegister';
import isEmpty from 'lodash/isEmpty';
/**
 *
 * @param {Object} auth {token, expiresIn}
 */
export function isTokenAlive() {
  const tokenObject = JSON.parse(localStorage.getItem(SA_TOKEN));

  if (isEmpty(tokenObject)) return false;

  const { expiresIn } = tokenObject;

  if (expiresIn > Date.now()) return true;
  return false;
}

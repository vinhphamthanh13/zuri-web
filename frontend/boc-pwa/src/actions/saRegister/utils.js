import { SA_TOKEN } from 'constants/saRegister';
import isEmpty from 'lodash/isEmpty';

export const getToken = () => {
  const tokenObject = JSON.parse(localStorage.getItem(SA_TOKEN));

  if (isEmpty(tokenObject)) return false;

  return tokenObject.token;
};

export const clearSAToken = () => {
  localStorage.removeItem(SA_TOKEN);
};

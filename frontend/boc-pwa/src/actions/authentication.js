import { SET_PHONE_NUMBER } from 'constants/authentication';

export const setPhoneNumberAction = payload => ({
  type: SET_PHONE_NUMBER,
  payload,
});

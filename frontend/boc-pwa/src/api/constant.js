import config from 'config';

const BASE_URL = config.masterData.url;
// const BASE_URL = 'http://68.183.188.1231:8080';

// Java Sever
export const SERVER_URL = {
  USERS: `${BASE_URL}/users`,
  GET_VERIFIED_CODE: `${BASE_URL}/user/:countryCode/:phoneNumber`,
  SET_VERIFIED_CODE: `${BASE_URL}/user/:countryCode/:phoneNumber/:token`,
  ACTIVATE_USER_BY_ID: `${BASE_URL}/users/active/:userId`,
  GET_USER_BY_ID: `${BASE_URL}/users/:userId`,
};

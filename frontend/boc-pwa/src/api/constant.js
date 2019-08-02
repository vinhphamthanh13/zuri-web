import config from 'config';

const BASE_URL = config.masterData.url;
const USERS = '/users';
const SEND_VERIFICATION_CODE = '/user/:countryCode/:phoneNumber';
const VERIFY_CODE = '/user/:countryCode/:phoneNumber/:token';
const ACTIVE_USER_BY_ID = '/users/active/:userId';
const USER_BY_ID = 'users/:userId';

// Java Sever
export const SERVER_URL = {
  LOGIN: `${BASE_URL}${USERS}`,
  GENERATE_VERIFICATION_CODE: `${BASE_URL}${SEND_VERIFICATION_CODE}`,
  VERIFY_USER_CODE: `${BASE_URL}${VERIFY_CODE}`,
  ACTIVATE_USER_BY_ID: `${BASE_URL}${ACTIVE_USER_BY_ID}`,
  FIND_USER_BY_ID: `${BASE_URL}${USER_BY_ID}`,
  CREATE_USER: `${BASE_URL}${USERS}`,
};

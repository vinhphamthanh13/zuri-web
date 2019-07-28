import config from 'config';

const BASE_URL = config.masterData.url;
const USERS = '/users';
const GENERATING_CODE = '/user/:countryCode/:phoneNumber';

// Java Sever
export const SERVER_URL = {
  LOGIN: `${BASE_URL}${USERS}`,
  VERIFY_CODE: `${BASE_URL}${GENERATING_CODE}`,
};

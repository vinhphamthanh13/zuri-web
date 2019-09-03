import config from 'config';

const BASE_URL = config.masterData.url;
const USERS = `${BASE_URL}/users`;
const AUTH = `${BASE_URL}/auth/users`;

// Java Sever
export const SERVER_URL = {
  AUTH,
  USERS,
  VERIFICATION_CODE: `${USERS}/:countryCode/:phoneNumber/:token`,
  CHECKING_USER: `${USERS}/checking`,
};

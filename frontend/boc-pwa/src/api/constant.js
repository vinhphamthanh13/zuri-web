import config from 'config';

const BASE_URL = config.masterData.url;
const USERS = `${BASE_URL}/users`;

// Java Sever
export const SERVER_URL = {
  USERS,
  VERIFYING_CODE: `${USERS}/:countryCode/:phoneNumber/:token`,
  ACTIVATE_USER_BY_ID: `${USERS}/active/:userId`,
  GET_USER_BY_ID: `${USERS}/:userId`,
};

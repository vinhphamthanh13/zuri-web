import config from 'config';

const BASE_URL = config.masterData.url;
const AUTH_URL = `${BASE_URL}/api`;

const USERS = `${BASE_URL}/users`;
const AUTH_USERS = `${BASE_URL}/auth/users`;

// Java Sever
export const SERVER_SITE_URL = {
  CREATING_USER: AUTH_USERS,
  SENDING_OTP: `${AUTH_USERS}/sendOTP`,
  VERIFYING_OTP: `${AUTH_USERS}/verifyOTP`,
  CREATING_STORE: `${AUTH_URL}/cua-hang`,
  LOGIN: `${AUTH_USERS}/login`,
  USERS,
  CHECKING_USER: `${USERS}/checking`,
};

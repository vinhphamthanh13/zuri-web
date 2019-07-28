import config from 'config';

const BASE_URL = config.masterData.url;
const USERS = '/users';

// Java Sever
export const SERVER_URL = {
  LOGIN: `${BASE_URL}${USERS}`,
};

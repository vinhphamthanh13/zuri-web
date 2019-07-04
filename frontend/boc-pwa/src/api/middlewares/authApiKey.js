import { HTTP_STATUS } from 'constants/http';

import config from '../../config';
import { AUTH_GET_METHOD_FAILED, AUTH_API_KEY_NOT_MATCHED } from './constant';

const { internalApiKey } = config.auth;

const authApiKey = (req, res, next) => {
  const { authorization } = req.headers;

  if (!authorization) {
    res
      .status(HTTP_STATUS.UNAUTHORIZED)
      .send({ error: AUTH_GET_METHOD_FAILED });
    return;
  }

  const values = authorization.split(' ');

  if (
    values.length === 2 &&
    values[0].toLowerCase() === 'apikey' &&
    values[1] === internalApiKey
  ) {
    next();
    return;
  }

  res
    .status(HTTP_STATUS.UNAUTHORIZED)
    .send({ error: AUTH_API_KEY_NOT_MATCHED });
};

export default authApiKey;

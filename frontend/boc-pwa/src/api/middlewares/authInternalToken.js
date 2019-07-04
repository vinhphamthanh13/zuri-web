import jwt from 'jsonwebtoken';
import { HTTP_STATUS } from 'constants/http';

import config from '../../config';
import { AUTH_GET_METHOD_FAILED, AUTH_TOKEN_FAILED } from './constant';

const { internalAuth } = config.auth;

const authInternalToken = (req, res, next) => {
  const { authorization } = req.headers;

  if (!authorization) {
    res
      .status(HTTP_STATUS.UNAUTHORIZED)
      .send({ error: AUTH_GET_METHOD_FAILED });
    return;
  }

  const values = authorization.split(' ');

  if (values.length === 2 && values[0].toLowerCase() === 'bearer') {
    try {
      const token = values[1];
      const user = jwt.verify(
        token,
        internalAuth.jwtSecret,
        internalAuth.jwtOptions,
      );
      if (user != null) {
        req.user = user;
        next();
        return;
      }
    } catch (err) {
      console.error(err);
      // do nothing
    }
  }

  res.status(HTTP_STATUS.UNAUTHORIZED).send({ error: AUTH_TOKEN_FAILED });
};

export default authInternalToken;

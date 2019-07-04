import jwt from 'jsonwebtoken';

import { HTTP_STATUS } from 'constants/http';
import config from '../../config';
import { AUTH_GET_METHOD_FAILED, AUTH_TOKEN_FAILED } from './constant';

const { userAuth } = config.auth;

const authToken = (req, res, next) => {
  const { authorization } = req.headers;

  if (!authorization) {
    res.status(HTTP_STATUS.UNAUTHORIZED).send({
      errors: [
        { code: HTTP_STATUS.UNAUTHORIZED, message: AUTH_GET_METHOD_FAILED },
      ],
    });
    return;
  }

  const values = authorization.split(' ');

  if (values.length === 2 && values[0].toLowerCase() === 'bearer') {
    try {
      const token = values[1];
      const user = jwt.verify(token, userAuth.jwtSecret, userAuth.jwtOptions);
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

  res.status(HTTP_STATUS.UNAUTHORIZED).send({
    errors: [{ code: HTTP_STATUS.UNAUTHORIZED, message: AUTH_TOKEN_FAILED }],
  });
};

export default authToken;

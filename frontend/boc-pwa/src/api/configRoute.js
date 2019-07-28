import { NODE_URL } from 'constants/api';
import userRouter from './user/router';
import login from './authentication/login';
import verificationCode from './authentication/verificationCode';

export default app => {
  app.use('/api/user', userRouter);
  app.get(`${NODE_URL.LOGIN}/:userId`, login);
  app.post(
    `${NODE_URL.VERIFY_CODE}/:countryCode/:phoneNumber`,
    verificationCode,
  );
};

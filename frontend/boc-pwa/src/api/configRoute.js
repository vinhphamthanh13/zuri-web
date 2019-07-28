import { NODE_URL } from 'constants/api';
import login from './authentication/login';
import userRouter from './user/router';

export default app => {
  app.get(`${NODE_URL.LOGIN}/:userId`, login);
  app.use('/api/user', userRouter);
};

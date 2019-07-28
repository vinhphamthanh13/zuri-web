import { NODE_URL } from 'constants/api';
import login from './authentication/login';

export default app => {
  app.get(`${NODE_URL.LOGIN}/:userId`, login);
};

import { NODE_SERVER_URL } from 'actions/constants';
import authController from './authenticationApi';

export default app => {
  app.use(NODE_SERVER_URL.AUTH.ROOT, authController);
};

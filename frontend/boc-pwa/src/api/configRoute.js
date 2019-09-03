import { NODE_SERVER_URL } from 'actions/api';
import userController from './authenticationApi';

export default app => {
  app.use(NODE_SERVER_URL.AUTHENTICATION.ROOT, userController);
};

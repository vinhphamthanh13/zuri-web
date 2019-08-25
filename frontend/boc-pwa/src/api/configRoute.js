import { NODE_SERVER_URL } from 'constants/api';
import userController from './authentication/authenticationApi';

export default app => {
  app.use(NODE_SERVER_URL.USERS, userController);
};

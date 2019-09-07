import { PROXY_AUTH } from 'actions/constants';
import authController from './authenticationApi';

export default app => {
  app.use(PROXY_AUTH, authController);
};

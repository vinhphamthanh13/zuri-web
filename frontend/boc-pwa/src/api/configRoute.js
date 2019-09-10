import { PROXY_AUTH, PROXY_SHOP } from 'actions/constants';
import authController from './authenticationApi';
import shopsController from './shopsApi';

export default app => {
  app.use(PROXY_AUTH, authController);
  app.use(PROXY_SHOP, shopsController);
};

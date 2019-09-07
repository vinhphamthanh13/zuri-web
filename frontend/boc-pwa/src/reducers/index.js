import { combineReducers } from 'redux';
import authentication from './authenticationReducer';
import user from './user';
import runtime from './runtime';
import intl from './intl';

import common from './commonReducer';

export default combineReducers({
  user,
  runtime,
  intl,
  common,
  authentication,
});

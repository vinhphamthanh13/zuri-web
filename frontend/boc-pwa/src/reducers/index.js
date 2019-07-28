import { combineReducers } from 'redux';

import user from './user';
import runtime from './runtime';
import intl from './intl';

import authentication from './authentication/authentication';

export default combineReducers({
  user,
  runtime,
  intl,
  authentication,
});

import { combineReducers } from 'redux';
import user from './user';
import runtime from './runtime';
import intl from './intl';

import authentication from './authentication/authentication';
import common from './common';

export default combineReducers({
  user,
  runtime,
  intl,
  common,
  authentication,
});

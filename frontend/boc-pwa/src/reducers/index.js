import { combineReducers } from 'redux';

import user from './user';
import runtime from './runtime';
import intl from './intl';
import internal from './internal';
import saRegister from './saRegister';

export default combineReducers({
  user,
  runtime,
  intl,
  internal,
  saRegister,
});

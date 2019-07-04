import { combineReducers } from 'redux';

import user from './user';
import runtime from './runtime';
import intl from './intl';
import clz from './clz';
import internal from './internal';
import saRegister from './saRegister';

export default combineReducers({
  user,
  runtime,
  intl,
  clz,
  internal,
  saRegister,
});

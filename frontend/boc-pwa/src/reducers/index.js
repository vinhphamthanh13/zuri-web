import { combineReducers } from 'redux';
import user from './user';
import runtime from './runtime';
import intl from './intl';
import common from './commonReducer';
import authentication from './authenticationReducer';
import shops from './shopsReducer';

export default combineReducers({
  user,
  runtime,
  intl,
  common,
  authentication,
  shops,
});

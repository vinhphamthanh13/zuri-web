import { combineReducers } from 'redux';
import { loading } from './loading';
import { alert } from './alert';
import { auth } from './auth';

export default combineReducers({
  auth,
  loading,
  alert,
});

import { combineReducers } from 'redux';
import { error, loading, alert } from './app';
import auth from './auth';
import sales from './sales';

export default combineReducers({
  auth,
  loading,
  error,
  alert,
  sales,
});

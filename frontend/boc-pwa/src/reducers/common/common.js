import storage from 'redux-persist/lib/storage';
import persistReducer from 'redux-persist/lib/persistReducer';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';
import { SET_LOADING, SET_ERROR, RESET_ERROR } from 'actions/common';

const persistConfig = {
  key: 'common',
  storage,
  stateReconciler: autoMergeLevel2,
  blacklist: ['isLoading', 'isError', 'errorMessage'],
};

const initState = {
  isLoading: null,
  isError: null,
  errorMessage: '',
};

const reducer = (state = initState, action) => {
  if (action.type === SET_LOADING) {
    return {
      ...state,
      isLoading: action.payload,
    };
  }
  if (action.type === SET_ERROR) {
    return {
      ...state,
      isError: true,
      errorMessage: action.payload,
    };
  }
  if (action.type === RESET_ERROR) {
    return {
      ...state,
      isError: null,
      errorMessage: '',
    };
  }

  return state;
};

export default persistReducer(persistConfig, reducer);

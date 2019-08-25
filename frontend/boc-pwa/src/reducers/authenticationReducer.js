import storage from 'redux-persist/lib/storage/index';
import persistReducer from 'redux-persist/lib/persistReducer';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';
import {
  SET_USERS,
  SET_PHONE_NUMBER,
  GET_VERIFIED_CODE,
} from 'actions/authenticationActions';

const persistConfig = {
  key: 'authentication',
  storage,
  stateReconciler: autoMergeLevel2,
  blacklist: ['getVerifiedCodeStatus'],
};

const initState = {
  users: [],
  phoneNumber: null,
  getVerifiedCodeStatus: null,
};

const reducer = (state = initState, action) => {
  if (action.type === SET_USERS) {
    return {
      ...state,
      users: action.payload,
    };
  }
  if (action.type === SET_PHONE_NUMBER) {
    return {
      ...state,
      phoneNumber: action.payload,
    };
  }
  if (action.type === GET_VERIFIED_CODE) {
    return {
      ...state,
      getVerifiedCodeStatus: action.payload,
    };
  }

  return state;
};

export default persistReducer(persistConfig, reducer);

import storage from 'redux-persist/lib/storage';
import persistReducer from 'redux-persist/lib/persistReducer';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';
import {
  SET_PHONE_NUMBER,
  SET_VERIFICATION_CODE,
  SET_USER_ID,
} from 'constants/authentication';

const persistConfig = {
  key: 'authentication',
  storage,
  stateReconciler: autoMergeLevel2,
  blacklist: [],
};

const initState = {
  phoneNumber: null,
  verificationCode: null,
  userId: null,
};

const reducer = (state = initState, action) => {
  if (action.type === SET_PHONE_NUMBER) {
    return {
      ...state,
      phoneNumber: action.payload,
    };
  }
  if (action.type === SET_VERIFICATION_CODE) {
    return {
      ...state,
      verificationCode: action.payload,
    };
  }
  if (action.type === SET_USER_ID) {
    return {
      ...state,
      userId: action.payload,
    };
  }

  return state;
};

export default persistReducer(persistConfig, reducer);

import storage from 'redux-persist/lib/storage';
import persistReducer from 'redux-persist/lib/persistReducer';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';
import {
  SET_USERS,
  SET_PHONE_NUMBER,
  SENDING_OTP,
  VERIFYING_OTP,
  VERIFYING_OTP_STATUS,
  EXISTING_USER,
  CREATING_USER,
  CREATING_STORE_INFO,
  CREATING_STORE,
  CREATING_STORE_STATUS,
  CREATING_STORE_PROGRESS,
  SETTING_USER_DETAIL,
  GETTING_USER,
} from 'actions/authenticationActions';

const persistConfig = {
  key: 'authentication',
  storage,
  stateReconciler: autoMergeLevel2,
  blacklist: ['sendingOTPStatus', 'existingUser', 'creatingUser'],
};

const initState = {
  users: [],
  phoneNumber: null,
  countryCode: null,
  encryptPhone: null,
  sendingOTPStatus: null,
  verifyingOTPStatus: null,
  accessToken: null,
  existingUser: { success: null },
  creatingUser: null,
  creatingStoreStatus: null,
  creatingStoreProgress: null,
  storeInfo: {},
  selectedShopId: '',
  userDetail: {},
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
      phoneNumber: action.payload.phoneNumber,
      encryptPhone: action.payload.encryptPhone,
      countryCode: action.payload.countryCode,
    };
  }
  if (action.type === SENDING_OTP) {
    return {
      ...state,
      sendingOTPStatus: action.payload,
    };
  }
  if (action.type === VERIFYING_OTP) {
    return {
      ...state,
      ...action.payload, // verifyingOTPStatus, accessToken, userDetail
    };
  }
  if (action.type === VERIFYING_OTP_STATUS) {
    return {
      ...state,
      verifyingOTPStatus: action.payload,
    };
  }
  // coupling with shopsReducer
  if (action.type === SETTING_USER_DETAIL) {
    return {
      ...state,
      userDetail: action.payload,
    };
  }
  if (action.type === GETTING_USER) {
    return {
      ...state,
      userDetail: action.payload,
    };
  }
  if (action.type === EXISTING_USER) {
    return {
      ...state,
      existingUser: action.payload,
    };
  }
  if (action.type === CREATING_USER) {
    return {
      ...state,
      creatingUser: action.payload,
    };
  }
  if (action.type === CREATING_STORE_STATUS) {
    return {
      ...state,
      creatingStoreStatus: action.payload,
    };
  }
  if (action.type === CREATING_STORE_INFO) {
    return {
      ...state,
      storeInfo: action.payload,
    };
  }
  if (action.type === CREATING_STORE) {
    return {
      ...state,
      ...action.payload, // creatingStoreStatus, storeInfo
    };
  }
  if (action.type === CREATING_STORE_PROGRESS) {
    return {
      ...state,
      creatingStoreProgress: action.payload,
    };
  }

  return state;
};

export default persistReducer(persistConfig, reducer);

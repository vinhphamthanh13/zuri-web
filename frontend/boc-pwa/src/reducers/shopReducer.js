import storage from 'redux-persist/lib/storage';
import persistReducer from 'redux-persist/lib/persistReducer';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';
import { GETTING_STORE } from 'actions/authenticationActions';

const persistConfig = {
  key: 'shopInfo',
  storage,
  stateReconciler: autoMergeLevel2,
  blacklist: [],
};

const initState = {
  gettingStoreInfo: null,
};

const reducer = (state = initState, action) => {
  if (action.type === GETTING_STORE) {
    return {
      ...state,
      gettingStoreInfo: action.payload,
    };
  }

  return state;
};

export default persistReducer(persistConfig, reducer);

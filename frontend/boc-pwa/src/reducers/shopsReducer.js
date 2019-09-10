import storage from 'redux-persist/lib/storage';
import persistReducer from 'redux-persist/lib/persistReducer';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';
import { GETTING_STORE, SELECTED_SHOP_ID } from 'actions/shopsActions';

const persistConfig = {
  key: 'shopInfo',
  storage,
  stateReconciler: autoMergeLevel2,
  blacklist: [],
};

const initState = {
  gettingStoreInfo: null,
  selectedShopId: '',
};

const reducer = (state = initState, action) => {
  if (action.type === GETTING_STORE) {
    return {
      ...state,
      gettingStoreInfo: action.payload,
    };
  }
  if (action.type === SELECTED_SHOP_ID) {
    return {
      ...state,
      selectedShopId: action.payload,
    };
  }

  return state;
};

export default persistReducer(persistConfig, reducer);

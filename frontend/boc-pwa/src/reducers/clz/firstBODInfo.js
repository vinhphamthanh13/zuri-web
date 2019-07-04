import {
  CLZ_UPDATE_1BOD_INFO,
  CLZ_CLEAR_1BOD_INFO,
  DEFAULT_CREDIT_AMOUNT,
  DEFAULT_TENOR,
} from 'constants/clz';
import storage from 'redux-persist/lib/storage';
import { persistReducer } from 'redux-persist';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';

const persistConfig = {
  key: 'firstBODInfo',
  storage,
  stateReconciler: autoMergeLevel2,
  blacklist: [
    'offerList',
    'bestOffer',
    'tenor',
    'creditAmount',
    'sa',
    'retailAgent',
    'partnerCode',
    'tipperCode',
  ],
};

const initial1BOD = {
  phoneNumber: '',
  acceptedTerm: false,
  otp: '',
  creditAmount: DEFAULT_CREDIT_AMOUNT,
  tenor: DEFAULT_TENOR,
  firstName: '',
  lastName: '',
  middleName: '',
  dayOfBirth: '',
  gender: 'MALE',
  idCard: '',
  userImage: undefined,
  monthlyIncome: '',
  monthlyPayment: 0,
  identificationType: '',
  drivingLicence: '',
  familyBook: '',
  verificationID: null,
  offerList: [],
  bestOffer: {},
  retailAgent: '',
  sa: null,
  partnerCode: null,
  tipperCode: null,
};

function firstBODInfo(state, action) {
  const newState = state || initial1BOD;

  if (action.type === CLZ_UPDATE_1BOD_INFO) {
    return { ...newState, [action.payload.label]: action.payload.value };
  }

  if (action.type === CLZ_CLEAR_1BOD_INFO) {
    return initial1BOD;
  }

  return newState;
}

export default persistReducer(persistConfig, firstBODInfo);

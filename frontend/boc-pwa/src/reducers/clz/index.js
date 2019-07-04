/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import { combineReducers } from 'redux';
import storage from 'redux-persist/lib/storage';
import { persistReducer } from 'redux-persist';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';

import updateFormStatus from './header';
import firstBODInfo from './firstBODInfo';
import updateLoader from './loading';
import step from './step';
import map from './map';
import { trackingToken } from './token';

const persistConfig = {
  key: 'clz',
  storage,
  stateReconciler: autoMergeLevel2,
  blacklist: ['step', 'loaderStatus', 'firstBODInfo'],
};

export default persistReducer(
  persistConfig,
  combineReducers({
    firstBODInfo,
    step,
    formStatus: updateFormStatus,
    loaderStatus: updateLoader,
    map,
    trackingToken,
  }),
);

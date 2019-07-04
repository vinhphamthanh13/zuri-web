/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import { CLZ_UPDATE_FORM_STATUS } from 'constants/clz';
import { FORM_STATUS } from 'constants/common';

export default function header(state, action) {
  const newState = state || FORM_STATUS.INITIAL;

  if (action.type === CLZ_UPDATE_FORM_STATUS) {
    return action.payload;
  }

  return newState;
}

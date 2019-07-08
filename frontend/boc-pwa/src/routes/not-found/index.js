/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';

import { HTTP_STATUS } from 'constants/http';
import NotFound from './NotFound';

const title = 'Page Not Found';

function action() {
  return {
    chunks: ['not-found'],
    title,
    component: <NotFound title={title} />,
    status: HTTP_STATUS.NOT_FOUND,
  };
}

export default action;

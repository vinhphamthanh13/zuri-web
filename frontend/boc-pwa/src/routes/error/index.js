/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import ErrorPage from './ErrorPage';

const action = () => ({
  title: 'Demo Error',
  component: <ErrorPage />,
});

export default action;

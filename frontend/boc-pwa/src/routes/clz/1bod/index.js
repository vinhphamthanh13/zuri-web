/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import Layout from 'components/Layout/Layout';

import Home from './Home';
import { TITLE_1BOD } from './constant';

function action(context, params) {
  return {
    title: TITLE_1BOD,
    component: (
      <Layout fullHeight fullWidth>
        <Home params={params} />
      </Layout>
    ),
  };
}

export default action;

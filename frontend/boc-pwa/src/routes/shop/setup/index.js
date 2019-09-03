/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import Layout from 'components/Layout';
import Setup from './Setup';

async function action() {
  return {
    title: 'Thiết lập cửa hàng',
    chunks: ['shopSetup'],
    component: (
      <Layout>
        <Setup />
      </Layout>
    ),
  };
}

export default action;

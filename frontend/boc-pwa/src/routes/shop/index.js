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
import Shop from './Shop';

async function action() {
  return {
    title: 'Cửa Hàng',
    chunks: ['shop'],
    component: (
      <Layout>
        <Shop news={[]} />
      </Layout>
    ),
  };
}

export default action;

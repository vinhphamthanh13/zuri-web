/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import Home from './Home';
import Layout from '../../components/Layout';

async function action() {
  return {
    title: 'http://www.bocvietnam.com',
    chunks: ['home'],
    component: (
      <Layout>
        <Home news={[]} />
      </Layout>
    ),
  };
}

export default action;

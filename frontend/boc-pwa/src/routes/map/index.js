/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import FindNearShop from './FindNearShop';
import Layout from '../../components/Layout';
import HeaderBar from './HeaderBar';

async function action(context) {
  return {
    title: 'http://www.bocvietnam.com',
    component: (
      <Layout fullHeight>
        <HeaderBar />
        <FindNearShop contractId={context.params.id} />
      </Layout>
    ),
  };
}

export default action;

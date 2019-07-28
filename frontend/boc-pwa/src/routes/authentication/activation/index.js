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
import Activation from 'routes/authentication/activation/Activation';

const action = () => ({
  chunks: ['login'],
  title: 'Kích Hoạt Tài Khoản',
  component: (
    <Layout>
      <Activation code="+84" />
    </Layout>
  ),
});

export default action;

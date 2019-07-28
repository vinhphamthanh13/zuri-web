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
import VerifyCode from './VerifyCode';

const action = () => ({
  chunks: ['verifyCode'],
  title: 'Mã Kích Hoạt',
  component: (
    <Layout>
      <VerifyCode />
    </Layout>
  ),
});

export default action;

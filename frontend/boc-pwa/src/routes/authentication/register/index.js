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
import Register from './Register';

const action = () => ({
  chunks: ['Register'],
  title: 'Đăng Ký BOCVN',
  component: (
    <Layout>
      <Register />
    </Layout>
  ),
});

export default action;

/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import Styleguide from './Styleguide';
import Layout from '../../components/Layout';

export default () => ({
  title: 'Styleguide page',
  chunks: ['home'],
  component: (
    <Layout>
      <Styleguide news={[]} />
    </Layout>
  ),
});

/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import HeaderBar from 'components/Header/HeaderBar';

import Layout from '../../components/Layout';
import GenerateLink from './GenerateLink';
import { HEADER_TITLE } from './constant';

async function action() {
  return {
    title: 'SA code',
    component: (
      <Layout fullHeight hiddenXs>
        <HeaderBar hideNextButton hideBackButton title={HEADER_TITLE} />
        <GenerateLink />
      </Layout>
    ),
  };
}

export default action;

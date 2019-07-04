/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import LayoutMobile from 'components/Layout/LayoutMobile';
import Home from './Home';

const title = 'Client zone';

function action() {
  return {
    title,
    component: (
      <LayoutMobile fullHeight>
        <Home />
      </LayoutMobile>
    ),
  };
}

export default action;

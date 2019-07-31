/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import s from './Shop.css';

class Shop extends React.Component {
  render() {
    return (
      <div className={s.container}>
        <h1>Cửa hàng của bạn!</h1>
      </div>
    );
  }
}

export default withStyles(s)(Shop);

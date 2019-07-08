/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import { node } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import s from './Layout.css';

class Layout extends React.Component {
  static propTypes = {
    children: node.isRequired,
  };

  render() {
    return <div className={s.layout}>{this.props.children}</div>;
  }
}

export default withStyles(s)(Layout);

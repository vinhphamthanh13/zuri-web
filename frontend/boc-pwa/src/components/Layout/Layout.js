/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import { node, bool } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import classnames from 'classnames';

import s from './Layout.css';

class Layout extends React.Component {
  static propTypes = {
    children: node.isRequired,
    fullHeight: bool,
  };

  static defaultProps = {
    fullHeight: false,
  };

  render() {
    const { fullHeight } = this.props;
    return (
      <div className={classnames(fullHeight && s.fullHeight, s.layout)}>
        {this.props.children}
      </div>
    );
  }
}

export default withStyles(s)(Layout);

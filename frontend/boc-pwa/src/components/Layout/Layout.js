/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
/* eslint-disable css-modules/no-unused-class */

import React from 'react';
import { node, number } from 'prop-types';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import windowSize from 'react-window-size';
import s from './Layout.css';

class Layout extends React.Component {
  static propTypes = {
    children: node.isRequired,
    windowWidth: number.isRequired,
    windowHeight: number.isRequired,
  };

  render() {
    const { windowWidth, windowHeight } = this.props;
    const layoutDimensionStyle = s[`w${windowWidth}H${windowHeight}`];
    return (
      <div className={`${s.layout} ${layoutDimensionStyle}`}>
        {this.props.children}
      </div>
    );
  }
}

const enhancers = [withStyles(s), windowSize];

export default compose(...enhancers)(Layout);

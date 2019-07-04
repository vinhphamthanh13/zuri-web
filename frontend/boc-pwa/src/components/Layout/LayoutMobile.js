/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import PropTypes from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import classnames from 'classnames';
import MessageNotSupport from './MessageNotSupport';

import s from './Layout.css';
import Header from '../Header';
import Footer from '../Footer';

class Layout extends React.Component {
  static propTypes = {
    children: PropTypes.node.isRequired,
    fullHeight: PropTypes.bool,
    fullWidth: PropTypes.bool,
  };

  static defaultProps = {
    fullHeight: false,
    fullWidth: false,
  };

  /**
   * This layout for mobile only
   * If desktop enter, got MessageNotSupport
   */
  render() {
    const { fullHeight, fullWidth } = this.props;
    return (
      <div
        className={classnames(s.layout, {
          [s.fullHeight]: fullHeight,
          [s.fullWidth]: fullWidth,
        })}
      >
        <Header hiddenXs />
        <div className={s.mobile}>{this.props.children}</div>
        <div className={s.desktop}>
          <MessageNotSupport />
        </div>
        <Footer hiddenXs />
      </div>
    );
  }
}

export default withStyles(s)(Layout);

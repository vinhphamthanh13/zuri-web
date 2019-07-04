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
import s from './HamburgerButton.css';

const HamburgerButton = ({ mobileMenuClosed, onClick }) => {
  const close = mobileMenuClosed ? '' : s.close;
  return (
    <button className={`${s.root} ${close}`} onClick={onClick}>
      <span />
      <span />
      <span />
    </button>
  );
};

HamburgerButton.propTypes = {
  mobileMenuClosed: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default withStyles(s)(HamburgerButton);

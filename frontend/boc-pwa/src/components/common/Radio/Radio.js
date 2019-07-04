/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

/* eslint-disable css-modules/no-unused-class */

import React from 'react';
import PropTypes from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './style.css';

const Radio = ({ checked, children, onMouseDown }) => {
  const className = checked ? s.radioChecked : s.radio;
  return (
    <div
      onMouseDown={onMouseDown}
      className={className}
      tabIndex="0"
      role="button"
    >
      {children}
    </div>
  );
};

Radio.propTypes = {
  checked: PropTypes.bool,
  children: PropTypes.node,
  onMouseDown: PropTypes.func,
};

Radio.defaultProps = {
  checked: false,
  children: null,
  onMouseDown: () => {},
};

export default withStyles(s)(Radio);

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
import { bool, node, func } from 'prop-types';
import classNames from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './style.css';

const Check = ({ checked, children, onMouseDown, error }) => {
  const className = classNames(s.check, checked && s.checked, {
    [s.error]: error,
  });

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

Check.propTypes = {
  error: bool,
  checked: bool,
  children: node,
  onMouseDown: func,
};

Check.defaultProps = {
  error: false,
  checked: false,
  children: null,
  onMouseDown: () => {},
};

export default withStyles(s)(Check);

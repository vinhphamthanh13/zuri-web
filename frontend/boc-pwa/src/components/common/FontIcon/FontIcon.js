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
import classnames from 'classnames';

const FontIcon = ({ children, className, value, ...other }) => {
  const classes = classnames(
    { 'material-icons': typeof value === 'string' },
    className,
  );
  return (
    <span className={classes} {...other}>
      {value}
      {children}
    </span>
  );
};

FontIcon.propTypes = {
  children: PropTypes.element,
  className: PropTypes.string,
  value: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
};

FontIcon.defaultProps = {
  children: null,
  className: '',
  value: '',
};

export default FontIcon;

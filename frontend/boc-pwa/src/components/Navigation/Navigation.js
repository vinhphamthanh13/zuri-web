/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import PropTypes from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Navigation.css';
import Link from '../Link';

const Navigation = ({ closeMobileMenu }) => {
  return (
    <ul className={s.root}>
      <li>
        <Link onClick={closeMobileMenu} className={s.link} to="/about">

        </Link>
      </li>
      <li>
        <Link onClick={closeMobileMenu} className={s.link} to="/contact">

        </Link>
      </li>
    </ul>
  )
}

Navigation.defaultProps = {
  closeMobileMenu: null,
};

Navigation.propTypes = {
  closeMobileMenu: PropTypes.func,
}

export default withStyles(s)(Navigation);

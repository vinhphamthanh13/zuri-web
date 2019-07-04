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
import s from './Header.css';
import Link from '../Link';
import logoUrl from './logo-inverse.png';

class Header extends React.Component {
  static propTypes = {
    hiddenXs: PropTypes.bool,
  };

  static defaultProps = {
    hiddenXs: false,
  };

  constructor() {
    super();
    this.state = {
      mobileMenuClosed: true,
    };

    this.mobileMenuContainer = React.createRef();
  }

  closeMobileMenu = () => {
    this.setState({ mobileMenuClosed: true });
  };

  handleHamburgerBtnClick = () => {
    this.setState({ mobileMenuClosed: !this.state.mobileMenuClosed });
  };

  render() {
    const rootClasses = classnames(s.root, {
      [s.hiddenXs]: this.props.hiddenXs,
    });

    return (
      <div className={rootClasses}>
        <div className={s.logo}>
          <Link to="/">
            <img src={logoUrl} height="40" alt="Homecredit" />
          </Link>
        </div>
      </div>
    );
  }
}

export default withStyles(s)(Header);

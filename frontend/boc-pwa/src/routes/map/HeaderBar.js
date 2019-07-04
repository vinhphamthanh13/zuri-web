import React, { Component } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import Link from '../../components/Link';
import s from './HeaderBar.css';

const HomecreditIcon = () => (
  <svg className={s.iconLogo} fill="#fff" viewBox="0 -14 153 153">
    <g>
      <path d="M76.503 0C34.255 0 0 32.96 0 73.637c0 14.343 4.261 27.734 11.634 39.052H48.53c-11.201-8.566-18.477-22.111-18.477-37.405 0-25.918 20.793-46.923 46.443-46.923s46.444 21.005 46.444 46.923c0 15.294-7.266 28.84-18.477 37.405h36.902C148.744 101.37 153 87.98 153 73.637 153 32.96 118.76 0 76.503 0" />
      <path d="M50.39 87.036c3.548 11.73 13.276 20.156 24.73 20.156 11.465 0 21.203-8.425 24.742-20.156H50.39" />
    </g>
  </svg>
);

class HeaderBar extends Component {
  render() {
    return (
      <div className={s.headerBar}>
        <Link to="/">
          <HomecreditIcon />
        </Link>
      </div>
    );
  }
}

export default withStyles(s)(HeaderBar);

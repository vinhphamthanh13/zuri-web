import React, { Component } from 'react';
import { string } from 'prop-types';
import { IconButton } from 'homecredit-ui';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Logo from '../Logo';
import UserHeader from './UserHeader';
import Auth from '../../common/Auth';
import s from './HeaderBar.css';

class HeaderBar extends Component {
  static propTypes = {
    title: string,
    className: string,
  };

  static defaultProps = {
    title: '',
    className: '',
  };

  render() {
    const { title, className } = this.props;

    return (
      <div className={className}>
        <header className={s.header}>
          <span />
          <span>
            <IconButton icon={<Logo />} />
          </span>
          <span>
            <Auth
              renderAuth={({ username }, logout) => (
                <UserHeader username={username} logout={logout} />
              )}
            />
          </span>
        </header>
        {title && <h1 className={s.title}>{title}</h1>}
      </div>
    );
  }
}

export default withStyles(s)(HeaderBar);

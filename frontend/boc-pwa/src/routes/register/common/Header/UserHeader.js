import React, { PureComponent } from 'react';
import { func, noop, string } from 'prop-types';
import { Button } from 'homecredit-ui';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './UserHeader.css';
import { USER_TEXT } from '../constant';

class UserHeader extends PureComponent {
  static propTypes = {
    username: string,
    logout: func,
  };

  static defaultProps = {
    username: '',
    logout: noop,
  };

  onLogoutClick = () => {
    this.props.logout();
  };

  render() {
    const { username } = this.props;
    return (
      <React.Fragment>
        <span className={s.welcomeText}>
          <span />
          <span className={s.welcome}>{USER_TEXT.welcome}</span> {username} !
        </span>
        <Button onClick={this.onLogoutClick} className={s.btnLogout}>
          <span className={s.btnLogoutText}>{USER_TEXT.logout}</span>
        </Button>
      </React.Fragment>
    );
  }
}

export default withStyles(s)(UserHeader);

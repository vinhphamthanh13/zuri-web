import React, { PureComponent } from 'react';
import { compose } from 'redux';
import { connect } from 'react-redux';
import { bool, func, any, objectOf, noop } from 'prop-types';
import { Button } from 'homecredit-ui';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { logout, checkAuth } from 'actions/internal';
import history from '../../../../history';
import s from './UserHeader.css';
import { USER_TEXT } from '../constant';

class UserHeader extends PureComponent {
  static propTypes = {
    checkAuth: func,
    logout: func,
    isCheckAuth: bool,
    auth: objectOf(any),
  };

  static defaultProps = {
    checkAuth: noop,
    logout: noop,
    isCheckAuth: false,
    auth: {},
  };

  componentWillMount() {
    const { isCheckAuth } = this.props;
    this.props.checkAuth(isCheckAuth, history);
  }

  onLogoutClick = () => {
    this.props.logout(history);
  };

  render() {
    const { auth } = this.props;
    return (
      <div>
        {auth &&
          auth.user.name && (
            <div>
              <span />
              <span className={s.welcomeText}>
                <span />
                {USER_TEXT.welcome} {auth.user.name} !
              </span>
              <Button onClick={this.onLogoutClick} className={s.btnLogout}>
                <span className={s.btnLogoutText}>{USER_TEXT.logout}</span>
              </Button>
            </div>
          )}
      </div>
    );
  }
}

function mapStateToProps({ internal }) {
  const { auth } = internal;
  return { auth };
}

const enhance = compose(
  connect(
    mapStateToProps,
    { checkAuth, logout },
  ),
  withStyles(s),
);

export default enhance(UserHeader);

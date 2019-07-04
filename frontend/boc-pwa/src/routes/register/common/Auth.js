import { connect } from 'react-redux';
import { string, shape, func } from 'prop-types';
import noop from 'lodash/noop';
import { clearUserInfo } from 'actions/saRegister';
import history from '../../../history';

function Auth(props) {
  const { auth, renderAuth, renderUnAuth, logout } = props;

  function handleLogout() {
    logout();
    history.push('/sa-register/login');
  }

  if (auth.username) {
    return renderAuth(auth, handleLogout);
  }
  return renderUnAuth();
}

Auth.defaultProps = {
  auth: {},
  logout: noop,
  renderLogined: () => null,
  renderUnAuth: () => null,
};

Auth.propTypes = {
  auth: shape({
    username: string,
  }),
  renderLogined: func.isRequired,
  renderUnAuth: func.isRequired,
  logout: func,
};

const mapStateToProps = ({ saRegister }) => ({ auth: saRegister.auth });

const mapDispatchToProps = dispatch => ({
  logout: () => dispatch(clearUserInfo()),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(Auth);

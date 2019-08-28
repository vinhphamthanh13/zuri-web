import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import BocLogo from 'assets/images/welcome_boc.png';
import BocGreet from 'assets/images/boc_greeting.png';
import { HowToReg } from 'constants/svg';
import Button from 'components/Button';
import history from '../../../history';
import s from './Login.css';

class Login extends React.Component {
  handleActivation = () => {
    history.push('/activation');
  };
  redirectRegister = () => {
    history.push('/activation', {
      register: true,
    });
  };

  render() {
    return (
      <div className={s.container}>
        <div className={s.welcome}>
          <img src={BocLogo} alt="boc-logo" className={s.logo} />
          <div className={s.title}>
            <img src={BocGreet} alt="Boc Brand" className={s.brand} />
            <div className={s.greeting}>Quản lý cửa hàng hiệu quả hơn</div>
          </div>
        </div>
        <div className={s.login}>
          <Button
            label="Đăng Nhập"
            className={s.button}
            onClick={this.handleActivation}
          >
            <HowToReg />
          </Button>
        </div>
        <div className={s.register}>
          Hoặc chưa có tài khoản?{' '}
          <span onClick={this.redirectRegister}>Đăng ký</span>
        </div>
        <div className={s.copyRight}>Bản quyền thuộc về BOCVN@2019</div>
      </div>
    );
  }
}

export default withStyles(s)(Login);

import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import BocLogo from 'assets/images/welcome_boc.png';
import BocGreet from 'assets/images/boc_greeting.png';
import { HowToReg } from 'constants/svg';
import Button from 'components/Button';
import { ROUTER_URL } from 'constants/routerUrl';
import { LS_REGISTER } from 'constants/common';
import { navigateTo } from 'utils/browser';
import s from './Login.css';

class Login extends React.Component {
  handleActivation = () => {
    navigateTo(ROUTER_URL.AUTH.ACTIVATION);
  };
  handleRegister = () => {
    navigateTo(ROUTER_URL.AUTH.ACTIVATION, {
      [LS_REGISTER]: true,
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
          <span onClick={this.handleRegister}>Đăng ký</span>
        </div>
        <div className={s.copyRight}>Bản quyền thuộc về BOCVN@2020</div>
      </div>
    );
  }
}

export default withStyles(s)(Login);

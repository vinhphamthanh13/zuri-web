/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
// import PropTypes from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import BocLogo from 'assets/images/welcome_boc.png';
import BocGreet from 'assets/images/boc_greeting.png';
import { HowToReg } from 'constants/svg';
import Button from 'components/common/Button';
import s from './Login.css';

class Login extends React.Component {
  render() {
    return (
      <div className={s.container}>
        <div className={s.welcome}>
          <img src={BocLogo} alt="boc-logo" className={s.logo} />
          <div className={s.title}>
            <img src={BocGreet} alt="Boc Brand" className={s.brand} />
            <div className={s.greeting}>Quản lý cửa hàng hiệu quả hơn!</div>
          </div>
        </div>
        <div className={s.cta}>
          <Button label="Đăng Nhập" className={s.button}>
            <HowToReg />
          </Button>
        </div>
      </div>
    );
  }
}

export default withStyles(s)(Login);

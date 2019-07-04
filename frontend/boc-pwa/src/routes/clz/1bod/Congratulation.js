/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import { CONGRATS } from './constant';
import s from './Congratulation.css';
import slogan from './images/slogan.svg';
import girl from './images/bg.png';

class Congratulation extends React.Component {
  render() {
    return (
      <div className={s.container}>
        <div className={s.wrapper}>
          <div className={s.logoWrapper}>
            <img src="../images/small-logo.svg" className={s.logo} alt="Logo" />
            <img src={slogan} className={s.slogan} alt="Slogan" />
          </div>

          <div className={s.verifyIcon} />

          <p className={s.title}>CHÚC MỪNG ĐĂNG KÝ THÀNH CÔNG</p>
          <p className={s.summary}>{CONGRATS.SUMMARY}</p>

          <div className={s.wrapperGirl}>
            <img src={girl} className={s.girl} alt="happy girl" />
          </div>
        </div>
      </div>
    );
  }
}

export default withStyles(s)(Congratulation);

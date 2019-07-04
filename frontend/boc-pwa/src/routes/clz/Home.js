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
import { Button } from 'homecredit-ui';

import history from '../../history';
import slogan from './slogan.svg';
import s from './Home.css';

class Home extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isDirty: false,
    };
    this.trackingBounceRate = this.trackingBounceRate.bind(this);
  }

  componentDidMount() {
    window.ga('send', 'event', {
      eventCategory: 'Welcome Page',
      eventAction: 'Visit_welcome',
      eventLabel: 'Welcome',
    });
    window.addEventListener('beforeunload', this.trackingBounceRate);
  }

  componentWillUnmount() {
    window.removeEventListener('beforeunload', this.trackingBounceRate);
  }

  trackingBounceRate() {
    !this.state.isDirty &&
      window.ga('send', {
        hitType: 'event',
        eventCategory: 'Welcome Page',
        eventAction: 'Bounce_rate',
        eventLabel: 'Welcome',
      });
  }

  handleClick = async () => {
    this.setState({ isDirty: true });
    history.push('/1bod');
  };

  render() {
    return (
      <div className={s.container}>
        <div>
          <img src="/images/small-logo.svg" className={s.logo} alt="Logo" />
          <img src={slogan} className={s.slogan} alt="Slogan" />
          <ul className={s.list}>
            <li>
              Vay tiền mặt lên đến <b>40</b> triệu đồng
            </li>
            <li>
              Lãi suất hấp dẫn chỉ từ <b>1.66%/ tháng</b>
            </li>
            <li>Dành cho khách hàng vay lần đầu tiên</li>
            <li>Dành cho khách hàng từ 20 đến 60 tuổi</li>
            <li>Chỉ cần CMND và Giấy phép lái xe/ Hộ khẩu</li>
            <li>Nhận tiền trong 2 tiếng sau khi ký hợp đồng</li>
          </ul>
          <div className={s.bottomBtn}>
            <Button onClick={this.handleClick} stretch className={s.btnStart}>
              Bắt đầu
            </Button>
          </div>
        </div>
      </div>
    );
  }
}

export default withStyles(s)(Home);

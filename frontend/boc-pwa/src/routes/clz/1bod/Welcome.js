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
import { func } from 'prop-types';
import noop from 'lodash/noop';

import slogan from './images/slogan.svg';
import s from './Welcome.css';
import girl from './images/bg.png';

class Home extends React.Component {
  static propTypes = {
    goToNextStep: func,
  };

  static defaultProps = {
    goToNextStep: noop,
  };

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
      eventAction: 'Visit_welcome_page',
      eventLabel: 'Welcome Page',
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
        eventLabel: 'Welcome Page',
      });
  }

  handleClick = async () => {
    this.props.goToNextStep();
  };

  render() {
    return (
      <div className={s.container}>
        <div className={s.wrapper}>
          <div className="container">
            <div className={s.logoWrapper}>
              <img src="/images/small-logo.svg" className={s.logo} alt="Logo" />
              <img src={slogan} className={s.slogan} alt="Slogan" />
            </div>
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
              <li>Có thể nhận tiền ngay sau khi ký hợp đồng</li>
            </ul>
            <div className={s.mobileFooter}>
              <div className={s.wrapperGirl}>
                <img
                  src={girl}
                  alt="http://www.bocvietnam.com Pleased Girl"
                  className={s.girlImg}
                />
              </div>
              <div className={s.bottomBtn}>
                <Button onClick={this.handleClick} className={s.btnStart}>
                  Bắt đầu vay ngay
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default withStyles(s)(Home);

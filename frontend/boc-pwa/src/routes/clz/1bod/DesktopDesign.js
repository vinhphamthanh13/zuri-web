/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React, { PureComponent } from 'react';
import { node } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import classnames from 'classnames';
import Header from 'components/Header';
import Footer from 'components/Footer';

import fourtyYears from './images/40_years.svg';
import arrowDown from './images/arrow_down.svg';

import iconDesktop1 from './images/desktop_icon_1.png';
import iconDesktop2 from './images/desktop_icon_2.png';
import iconDesktop3 from './images/desktop_icon_3.png';

import s from './DesktopDesgin.css';

class DesktopDesign extends PureComponent {
  static propTypes = {
    children: node,
  };

  static defaultProps = {
    children: null,
  };

  scrollIntoScreenView = () => {
    const element = document.getElementById('main-view');
    const { offsetTop } = element;

    window.scroll({
      top: offsetTop,
      left: 0,
      behavior: 'smooth',
    });
  };

  render() {
    const { children } = this.props;

    return (
      <React.Fragment>
        <div className={s.mainContent}>
          <div className={s.header}>
            <Header />
          </div>
          <div className={classnames(s.headerDesktop, 'container')}>
            <div className={s.benefits}>
              <img src={fourtyYears} alt="40 years" />
              <div className={s.benefitsDescription}>
                <p>
                  Vay tiền
                  <strong>Nhanh, An Toàn</strong>
                  <strong>& Đơn giản</strong>
                </p>
                <ul>
                  <li>
                    Đăng ký khoản vay chỉ trong <strong>2 phút</strong>
                  </li>
                  <li>Nhận tiền ngay</li>
                </ul>
              </div>
            </div>

            <div className={s.benefitsSecond}>
              <button onClick={this.scrollIntoScreenView}>
                TÔI MUỐN VAY TIỀN <img src={arrowDown} alt="arrow down" />
              </button>

              <p>
                Chỉ với <strong>3 bước đơn giản</strong> để vay tiền mặt
              </p>
            </div>
          </div>

          <div className="container" id="main-view">
            <div className={classnames('row', s.boxWrapper)}>
              <div className={classnames('col-xl-3', s.boxAside, s.boxIcon)}>
                <img src={iconDesktop1} alt="benefit" />
                <img src={iconDesktop2} alt="benefit" />
                <img src={iconDesktop3} alt="benefit" />
              </div>
              <div
                className={classnames('col-xl-5 col-12', s.boxMain)}
                style={{ position: 'relative' }}
              >
                <div className={s.componentContent}>
                  <div className={s.componentBorder}>{children}</div>
                </div>
              </div>
              <div
                className={classnames('col-xl-3', s.desktopBoxs, s.boxAside)}
              >
                <div className={s.desktopBoxAside}>
                  <p className={s.boxAsideTitle}>Đối Tượng Vay</p>
                  <p className={s.boxAsideDescription}>
                    • Mọi công dân Việt Nam trong độ tuổi từ 20 tới 60 tuổi và
                    không phải là khách hàng hiện hữu của http://www.bocvietnam.com
                  </p>
                </div>
                <div className={s.desktopBoxAside}>
                  <p className={s.boxAsideTitle}>Hồ sơ vay rất đơn giản</p>
                  <p className={s.boxAsideDescription}>
                    • Chỉ cần CMND và Bằng Lái hoặc Hộ Khẩu
                  </p>
                </div>
                <div className={s.desktopBoxAside}>
                  <p className={s.boxAsideTitle}>Thời gian nhận tiền nhanh</p>
                  <p className={s.boxAsideDescription}>
                    • Bạn có thể nhận ngay sau khi hoàn tất hợp đồng
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div className={s.footer}>
            <Footer />
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default withStyles(s)(DesktopDesign);

import React, { PureComponent } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import sadIco from './images/sad-ico.svg';

import s from './InvailUrlMessage.css';

class invaildUrlMessage extends PureComponent {
  render() {
    return (
      <div className={s.container}>
        <img src={sadIco} alt="sadFace" className={s.sadFace} />
        <p className={s.paragraph}>Đã quá hạn 7 ngày đăng ký.</p>
        <p className={s.paragraph}>
          Quý khách vui lòng liên hệ nhân viên home Credit gần nhất để được hổ
          trợ.
        </p>
      </div>
    );
  }
}

export default withStyles(s)(invaildUrlMessage);

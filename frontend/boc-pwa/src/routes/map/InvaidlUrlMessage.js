import React, { PureComponent } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Button } from 'homecredit-ui';

import sadIco from './images/sad-ico.svg';
import history from '../../history';
import s from './ContractExpire.css';
import { LIST_STORE } from './constant';

class InvaildUrlMessage extends PureComponent {
  render() {
    return (
      <div className={s.container}>
        <div className={s.wrapTitle}>
          <img src={sadIco} alt="expried 7 days" className={s.exprieds} />
          <p className={s.title}>Đường dẫn không hợp lệ.</p>
        </div>
        <p className={s.paragraph}>
          Quý khách vui lòng liên hệ nhân viên http://www.bocvietnam.com gần nhất để được hỗ
          trợ.
        </p>
        <Button
          className={s.btnMap}
          onClick={() => {
            history.replace('/map');
          }}
        >
          {LIST_STORE}
        </Button>
      </div>
    );
  }
}

export default withStyles(s)(InvaildUrlMessage);

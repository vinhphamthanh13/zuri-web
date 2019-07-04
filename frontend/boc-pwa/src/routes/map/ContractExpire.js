import React, { PureComponent } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Button } from 'homecredit-ui';
import Link from 'components/Link';

import history from '../../history';
import expried from './images/expried.png';
import s from './ContractExpire.css';
import { LIST_STORE } from './constant';

class ContractExpire extends PureComponent {
  render() {
    return (
      <div className={s.container}>
        <div className={s.wrapTitle}>
          <img src={expried} alt="expried 7 days" className={s.exprieds} />
          <p className={s.title}>Đơn đăng ký vay đã hết hạn.</p>
        </div>
        <p className={s.paragraph}>
          Quý khách vui lòng đăng ký đơn vay khác tại{' '}
          <Link className={s.link} to="/">
            đây
          </Link>
          .
        </p>
        <p className={s.paragraph}>
          Hoặc liên hệ nhân viên http://www.bocvietnam.com gần nhất để được hỗ trợ.
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

export default withStyles(s)(ContractExpire);

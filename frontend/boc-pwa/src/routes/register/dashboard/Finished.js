import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import moment from 'moment';
import { string, func, boolean } from 'prop-types';
import noop from 'lodash/noop';
import { Button } from 'homecredit-ui';

import s from './Finished.css';

function Finished(props) {
  const { createAt, showUnRegister, unRegister } = props;
  const createAtMoment = moment(createAt);

  return (
    <React.Fragment>
      <div className={s.finished}>
        <p className={s.phrase}>
          Bạn đã thực hiện đăng ký thiết bị vào lúc{' '}
          {createAtMoment.format('HH:mm')} ngày{' '}
          {createAtMoment.format('DD-MM-YYYY')}.
        </p>
        <p className={s.phrase}>Xin Cảm ơn!</p>
      </div>
      {showUnRegister && (
        <Button onClick={unRegister} className={s.btnUnRegister}>
          Hủy đăng ký
        </Button>
      )}
    </React.Fragment>
  );
}

Finished.defaultProps = {
  createAt: new Date(),
  unRegister: noop,
  showUnRegister: false,
};

Finished.propTypes = {
  createAt: string,
  unRegister: func,
  showUnRegister: boolean,
};

export default withStyles(s)(Finished);

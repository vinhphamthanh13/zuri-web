import React from 'react';
import brand from 'assets/images/boc_greeting.png';
import logo from 'assets/images/welcome_boc.png';
import Button from 'components/Button';
import { Home } from 'constants/svg';
import { navigateTo } from 'utils/browser';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './NotFound.css';

const NotFound = () => {
  const goHome = () => navigateTo('/');
  return (
    <div className={s.unSupport}>
      <div className={s.logo}>
        <img src={logo} alt="BOC VN" width="100%" />
      </div>
      <p>Trang bạn đang tìm hiện chưa tồn tại trên BOCVN.</p>
      <p className={s.notFound}>404</p>
      <Button icon onClick={goHome}>
        <Home />
      </Button>
      <div className={s.copyRight}>
        <span>&copy;2020</span>
        <img src={brand} alt="Copy Right BOC" width="70%" />
      </div>
    </div>
  );
};

export default withStyles(s)(NotFound);

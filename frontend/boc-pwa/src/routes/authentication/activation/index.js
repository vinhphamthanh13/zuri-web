import React from 'react';
import Activation from 'routes/authentication/activation/Activation';
import { getLocationState, navigateTo } from 'utils/browser';
import { ROUTER_URL } from 'constants/routerUrl';
import { LS_REGISTER } from 'constants/common';
import Layout from 'components/Layout';

const action = () => {
  const onClickLeft = () => {
    navigateTo(ROUTER_URL.AUTH.LOGIN);
  };
  const register = getLocationState(LS_REGISTER);
  const title = register ? 'Đăng ký tài khoản' : 'Kích Hoạt Tài Khoản';
  const headerProps = {
    title,
    iconLeft: true,
    onClickLeft,
  };

  return {
    chunks: ['login'],
    title,
    component: (
      <Layout headerProps={headerProps}>
        <Activation code="+84" />
      </Layout>
    ),
  };
};

export default action;

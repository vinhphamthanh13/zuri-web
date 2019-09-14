import React from 'react';
import Layout from 'components/Layout';
import { LS_COME_BACK, LS_REGISTER } from 'constants/common';
import { navigateTo, getLocationState } from 'utils/browser';
import { ROUTER_URL } from 'constants/routerUrl';
import RegisterShop from './RegisterShop';

const action = () => {
  const title = 'Tạo cửa hàng mới';
  const comeBack = getLocationState(LS_COME_BACK) || ROUTER_URL.AUTH.SHOPS;
  const isRegistering = getLocationState(LS_REGISTER);
  const onClickLeft = () => {
    navigateTo(comeBack, {
      [LS_REGISTER]: isRegistering,
    });
  };

  const headerProps = {
    title,
    iconLeft: true,
    onClickLeft,
    gutter: true,
  };

  return {
    chunks: ['registerShop'],
    title,
    component: (
      <Layout headerProps={headerProps}>
        <RegisterShop />
      </Layout>
    ),
  };
};

export default action;

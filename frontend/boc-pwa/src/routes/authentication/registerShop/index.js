import { ROUTER_URL } from 'constants/routerUrl';
import React from 'react';
import Layout from 'components/Layout';
import { navigateTo } from 'utils/browser';
import RegisterShop from './RegisterShop';

const action = () => {
  const title = 'Tạo cửa hàng mới';
  const onClickLeft = () => {
    navigateTo(ROUTER_URL.AUTH.ACTIVATION);
  };

  const headerProps = {
    title,
    iconLeft: true,
    onClickLeft,
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

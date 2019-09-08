import React from 'react';
import Layout from 'components/Layout';
import { navigateTo } from 'utils/browser';
import { ROUTER_URL } from 'constants/routerUrl';
import VerifyOTP from 'routes/authentication/verifyOTP/VerifyOTP';

const action = () => {
  const title = 'Mã kích hoạt OTP';
  const onClickLeft = () => navigateTo(ROUTER_URL.AUTH.ACTIVATION);
  const headerProps = {
    title,
    iconLeft: true,
    onClickLeft,
  };

  return {
    chunks: ['verifyOTP'],
    title,
    component: (
      <Layout headerProps={headerProps}>
        <VerifyOTP />
      </Layout>
    ),
  };
};

export default action;

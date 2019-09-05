import React from 'react';
import Layout from 'components/Layout';
import VerifyOTP from 'routes/authentication/verifyOTP/VerifyOTP';

const action = () => ({
  chunks: ['verifyOTP'],
  title: 'Mã Kích Hoạt',
  component: (
    <Layout>
      <VerifyOTP />
    </Layout>
  ),
});

export default action;

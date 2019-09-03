import React from 'react';
import Layout from 'components/Layout';
import Activation from 'routes/authentication/activation/Activation';

const action = () => ({
  chunks: ['login'],
  title: 'Kích Hoạt Tài Khoản',
  component: (
    <Layout>
      <Activation code="+84" />
    </Layout>
  ),
});

export default action;

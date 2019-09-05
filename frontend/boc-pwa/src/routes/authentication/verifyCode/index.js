import React from 'react';
import Layout from 'components/Layout';
import VerifyCode from './VerifyCode';

const action = () => ({
  chunks: ['verifyCode'],
  title: 'Mã Kích Hoạt',
  component: (
    <Layout>
      <VerifyCode />
    </Layout>
  ),
});

export default action;

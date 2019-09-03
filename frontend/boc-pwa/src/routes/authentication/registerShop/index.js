import React from 'react';
import Layout from 'components/Layout';
import RegisterShop from './RegisterShop';

const action = () => ({
  chunks: ['registerShop'],
  title: 'Tạo cửa hàng',
  component: (
    <Layout>
      <RegisterShop />
    </Layout>
  ),
});

export default action;

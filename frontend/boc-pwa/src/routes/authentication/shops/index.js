import React from 'react';
import Layout from 'components/Layout';
import Shops from './Shops';

async function action() {
  return {
    title: 'Danh sách cửa hàng',
    chunks: ['shops'],
    component: (
      <Layout>
        <Shops />
      </Layout>
    ),
  };
}

export default action;

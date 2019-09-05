import React from 'react';
import Layout from 'components/Layout';
import Goods from './Goods';

async function action() {
  return {
    title: 'Mặt hàng',
    chunks: ['shopGoods'],
    component: (
      <Layout>
        <Goods />
      </Layout>
    ),
  };
}

export default action;

import React from 'react';
import Layout from 'components/Layout';
import Shop from './Shop';

async function action() {
  return {
    title: 'Cửa Hàng',
    chunks: ['shop'],
    component: (
      <Layout>
        <Shop />
      </Layout>
    ),
  };
}

export default action;

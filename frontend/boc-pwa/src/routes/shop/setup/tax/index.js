import React from 'react';
import Layout from 'components/Layout';
import Tax from './Tax';

async function action() {
  return {
    title: 'Thiết lập thuế',
    chunks: ['shopTax'],
    component: (
      <Layout>
        <Tax />
      </Layout>
    ),
  };
}

export default action;

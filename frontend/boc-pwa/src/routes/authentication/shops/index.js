import React from 'react';
import Layout from 'components/Layout';
import Shops from './Shops';

async function action() {
  const title = 'Danh sách cửa hàng';
  const headerProps = {
    title,
  };

  return {
    title,
    chunks: ['shops'],
    component: (
      <Layout headerProps={headerProps}>
        <Shops />
      </Layout>
    ),
  };
}

export default action;

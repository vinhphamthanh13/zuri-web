import React from 'react';
import Layout from 'components/Layout';
import BocTabs from 'components/BocTabs';
import Shop from './Shop';

const headerProps = {
  title: 'Cửa hàng của tôi',
  gutter: true,
};

async function action() {
  return {
    title: 'Cửa Hàng',
    chunks: ['shop'],
    component: (
      <Layout headerProps={headerProps} isTab>
        <Shop />
        <BocTabs activeIndex={3} />
      </Layout>
    ),
  };
}

export default action;

import React from 'react';
import BocTabs from 'components/BocTabs';
import Home from './Home';
import Layout from '../../components/Layout';

const headerProps = {
  title: 'Tổng quan',
  gutter: true,
};

async function action() {
  return {
    title: 'Tổng quan cửa hàng',
    chunks: ['home'],
    component: (
      <Layout headerProps={headerProps} isTab>
        <Home />
        <BocTabs activeIndex={0} />
      </Layout>
    ),
  };
}

export default action;

import React from 'react';
import Layout from 'components/Layout';
import BocTabs from 'components/BocTabs';
import Activity from './Activity';

const headerProps = {
  title: 'Hoạt động',
  gutter: true,
};

async function action() {
  return {
    title: 'Hoạt Động',
    chunks: ['activity'],
    component: (
      <Layout headerProps={headerProps} isTab>
        <Activity />
        <BocTabs activeIndex={2} />
      </Layout>
    ),
  };
}

export default action;

import BocTabs from 'components/BocTabs';
import React from 'react';
import Layout from 'components/Layout';
import Report from './Report';

const headerProps = {
  title: 'Báo cáo',
  gutter: true,
};

async function action(context, params) {
  return {
    title: 'Báo Cáo',
    chunks: ['report'],
    component: (
      <Layout headerProps={headerProps} isTab>
        <Report params={params} />
        <BocTabs activeIndex={1} />
      </Layout>
    ),
  };
}

export default action;

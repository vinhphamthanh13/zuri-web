import React from 'react';
import Layout from 'components/Layout';
import { ROUTER_URL } from 'constants/routerUrl';
import { navigateTo } from 'utils/browser';
import Setup from './Setup';

const title = 'Thiết lập cửa hàng';

const onClickLeft = () => {
  navigateTo(ROUTER_URL.TABS.SHOP);
};

const headerProps = {
  title,
  gutter: true,
  iconLeft: true,
  onClickLeft,
};

async function action() {
  return {
    title,
    chunks: ['shopSetup'],
    component: (
      <Layout headerProps={headerProps}>
        <Setup />
      </Layout>
    ),
  };
}

export default action;

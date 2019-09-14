import React from 'react';
import Layout from 'components/Layout';
import { goBack } from 'utils/browser';
import Goods from './Goods';

const action = async () => {
  const title = 'Mặt hàng';
  const headerProps = {
    title,
    gutter: true,
    iconLeft: true,
    onClickLeft: goBack,
  };

  return {
    title,
    chunks: ['shopGoods'],
    component: (
      <Layout headerProps={headerProps}>
        <Goods />
      </Layout>
    ),
  };
};

export default action;

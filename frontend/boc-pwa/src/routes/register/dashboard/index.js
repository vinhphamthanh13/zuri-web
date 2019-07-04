import React from 'react';

import Layout from 'components/Layout';
import HeaderBar from '../common/Header/HeaderBar';
import Alert from '../common/Alert';
import { PAGE_TITLE, HEADER_TITLE } from './constant';
import Home from './Home';
import Loading from '../common/Loading';

function action() {
  return {
    title: PAGE_TITLE,
    component: (
      <Layout fullHeight hiddenXs>
        <HeaderBar title={HEADER_TITLE} />
        <Home />
        <Loading />
        <Alert />
      </Layout>
    ),
  };
}

export default action;

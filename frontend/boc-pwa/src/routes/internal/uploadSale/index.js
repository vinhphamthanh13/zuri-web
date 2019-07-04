import React from 'react';

import Layout from 'components/Layout';
import HeaderBar from '../common/Header/HeaderBar';
import UploadSale from './UploadSale';
import Loading from '../common/Loading';
import Alert from '../common/Alert';
import { PAGE_TITLE, HEADER_TITLE } from './constant';

function action() {
  return {
    title: PAGE_TITLE,
    component: (
      <Layout fullHeight hiddenXs>
        <HeaderBar
          hideNextButton
          hideBackButton
          title={HEADER_TITLE}
          isCheckAuth
        />
        <UploadSale />
        <Loading />
        <Alert />
      </Layout>
    ),
  };
}

export default action;

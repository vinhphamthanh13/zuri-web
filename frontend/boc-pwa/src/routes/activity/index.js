import React from 'react';
import Layout from 'components/Layout';
import Activity from './Activity';

async function action() {
  return {
    title: 'Hoạt Động',
    chunks: ['activity'],
    component: (
      <Layout>
        <Activity />
      </Layout>
    ),
  };
}

export default action;

import React from 'react';
import Layout from 'components/Layout';
import Login from './Login';

const action = () => ({
  chunks: ['login'],
  title: 'Welcome BOCVN',
  component: (
    <Layout headerOff>
      <Login />
    </Layout>
  ),
});

export default action;

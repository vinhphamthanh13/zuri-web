import React from 'react';
import Layout from 'components/Layout';
import Register from './Register';

const action = () => ({
  chunks: ['register'],
  title: 'Đăng Ký BOCVN',
  component: (
    <Layout>
      <Register />
    </Layout>
  ),
});

export default action;

import React from 'react';
import { HTTP_STATUS } from 'constants/http';
import NotFound from './NotFound';

function action() {
  return {
    title: 'Lỗi không tìm thấy trang',
    chunks: ['not-found'],
    component: <NotFound />,
    status: HTTP_STATUS.NOT_FOUND,
  };
}

export default action;

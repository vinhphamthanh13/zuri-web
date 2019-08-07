import React from 'react';
import {
  Store,
  List,
  ViewStream,
  SupervisedUser,
  MonetizationOn,
  Print,
} from 'constants/svg';
import {
  triad01,
  triad02,
  triad03,
  triad04,
  triad06,
  triad08,
} from 'constants/colors';

export const SHOP = {
  MENU: [
    {
      icon: <Store size={36} hexColor={triad01} />,
      name: 'Thiết lập cửa hàng',
    },
    {
      icon: <List size={36} hexColor={triad02} />,
      name: 'Mặt hàng',
    },
    {
      icon: <ViewStream size={36} hexColor={triad03} />,
      name: 'Hàng tồn',
    },
    {
      icon: <SupervisedUser size={36} hexColor={triad04} />,
      name: 'Nhân viên',
    },
    {
      icon: <MonetizationOn size={36} hexColor={triad06} />,
      name: 'Thanh toán',
    },
    {
      icon: <Print size={36} hexColor={triad08} />,
      name: 'Đặt hàng giấy in',
    },
  ],
};

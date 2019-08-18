import React from 'react';
import {
  Store,
  SettingApps,
  Assignment,
  ViewStream,
  SupervisedUser,
  MonetizationOn,
  Receipt,
  PersonalPin,
  HeadsetMic,
} from 'constants/svg';
import {
  triad01,
  triad02,
  triad03,
  triad04,
  triad05,
  triad06,
  triad08,
} from 'constants/colors';
import history from '../history';

export const SHOP = {
  CATEGORY: {
    MANAGEMENT: {
      NAME: 'Quản lý cửa hàng',
      MENU: [
        {
          icon: <SettingApps size={36} hexColor={triad01} />,
          name: 'Thiết lập',
          action: () => history.push('/shop/setup'),
        },
        {
          icon: <Assignment size={36} hexColor={triad02} />,
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
          icon: <PersonalPin size={36} hexColor={triad05} />,
          name: 'Thành viên',
        },
      ],
    },
    SERVICE: {
      NAME: 'Các dịch vụ khác',
      MENU: [
        {
          icon: <MonetizationOn size={36} hexColor={triad06} />,
          name: 'Thanh toán',
        },
        {
          icon: <Receipt size={36} hexColor={triad03} />,
          name: 'Giấy in',
        },
      ],
    },
    SUPPORT: {
      NAME: 'Hỗ trợ khách hàng',
      MENU: [
        {
          icon: <HeadsetMic size={36} hexColor={triad08} />,
          name: 'Hỗ trợ',
        },
      ],
    },
  },
  SETUP: {
    STORE: {
      MENU: [
        {
          icon: <Store size={28} hexColor={triad01} />,
          name: 'Thuế',
          action: () => history.push('/shop/setup/tax'),
        },
        {
          icon: <Store size={28} hexColor={triad01} />,
          name: 'Phí dịch vụ',
          action: () => history.push('/shop/setup/service'),
        },
        {
          icon: <Store size={28} hexColor={triad01} />,
          name: 'Đơn hàng mở',
          action: () => history.push('/shop/setup/openedOrder'),
        },
        {
          icon: <Store size={28} hexColor={triad01} />,
          name: 'Máy in',
          action: () => history.push('/shop/setup/printer'),
        },
        {
          icon: <Store size={28} hexColor={triad01} />,
          name: 'Thông tin cửa hàng',
          action: () => history.push('/shop/setup/shopInfo'),
        },
      ],
      TAX: [
        {
          icon: <MonetizationOn size={28} hexColor={triad01} />,
          name: 'Thuế VAT đã đăng ký',
        },
      ],
      DETAIL: [],
    },
  },
};

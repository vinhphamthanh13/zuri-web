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
          ICON: <SettingApps size={36} hexColor={triad01} />,
          NAME: 'Thiết lập',
          action: () => history.push('/shop/setup'),
        },
        {
          ICON: <Assignment size={36} hexColor={triad02} />,
          NAME: 'Mặt hàng',
          action: () => history.push('/shop/goods'),
        },
        {
          ICON: <ViewStream size={36} hexColor={triad03} />,
          NAME: 'Hàng tồn',
        },
        {
          ICON: <SupervisedUser size={36} hexColor={triad04} />,
          NAME: 'Nhân viên',
        },
        {
          ICON: <PersonalPin size={36} hexColor={triad05} />,
          NAME: 'Thành viên',
        },
      ],
    },
    SERVICE: {
      NAME: 'Các dịch vụ khác',
      MENU: [
        {
          ICON: <MonetizationOn size={36} hexColor={triad06} />,
          NAME: 'Thanh toán',
        },
        {
          ICON: <Receipt size={36} hexColor={triad03} />,
          NAME: 'Giấy in',
        },
      ],
    },
    SUPPORT: {
      NAME: 'Hỗ trợ khách hàng',
      MENU: [
        {
          ICON: <HeadsetMic size={36} hexColor={triad08} />,
          NAME: 'Hỗ trợ',
        },
      ],
    },
  },
  SETUP: {
    STORE: {
      MENU: [
        {
          ICON: <MonetizationOn size={28} hexColor={triad06} />,
          NAME: 'Thuế',
          action: () => history.push('/shop/setup/tax'),
        },
        {
          ICON: <Store size={28} hexColor={triad01} />,
          NAME: 'Phí dịch vụ',
          action: () => history.push('/shop/setup/service'),
        },
        {
          ICON: <Store size={28} hexColor={triad01} />,
          NAME: 'Đơn hàng mở',
          action: () => history.push('/shop/setup/openedOrder'),
        },
        {
          ICON: <Store size={28} hexColor={triad01} />,
          NAME: 'Máy in',
          action: () => history.push('/shop/setup/printer'),
        },
        {
          ICON: <Store size={28} hexColor={triad01} />,
          NAME: 'Thông tin cửa hàng',
          action: () => history.push('/shop/setup/shopInfo'),
        },
      ],
      TAX: [
        {
          ICON: <MonetizationOn size={28} hexColor={triad06} />,
          NAME: 'Thuế VAT đã đăng ký',
        },
      ],
      DETAIL: [],
    },
  },
};

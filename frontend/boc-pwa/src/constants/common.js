export const REGEXP = {
  PHONE_NUMBER: /^\d{10,11}$/,
  COUNTRY_CODE: /^\+\d{2,3}$/,
  VERIFY_CODE: /^\d{6}$/,
  ENCRYPT_PHONE: /(\d{2})\d{5}(\d+)/,
};

export const INPUT = {
  USER_NAME: {
    label: 'Tên:',
    value: 'userName',
    placeholder: 'Vui lòng nhập tên',
  },
  LAST_NAME: {
    label: 'Họ:',
    value: 'lastName',
    placeholder: 'Vui lòng nhập họ',
  },
  PHONE_NUMBER: {
    label: 'Số điện thoại:',
    value: 'phoneNumber',
    placeholder: 'Vui lòng nhập số điện thoại',
  },
  SHOP_ADDRESS: {
    label: 'Địa chỉ cửa hàng:',
    value: 'shopAddress',
    placeholder: 'Nhập địa chỉ cửa hàng',
  },
};

export const TABS = {
  GENERAL: {
    name: 'tổng quan',
    url: '/home',
  },
  REPORT: {
    name: 'báo cáo',
    url: '/report',
  },
  ACTIVITY: {
    name: 'hoạt động',
    url: '/activity',
  },
  SHOP: {
    name: 'cửa hàng',
    url: '/shop',
  },
};

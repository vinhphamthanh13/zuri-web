export const REGEXP = {
  PHONE_NUMBER: /^\d{10,11}$/,
  COUNTRY_CODE: /^\+\d{2,3}$/,
  VERIFY_CODE: /^\d{6}$/,
  ENCRYPT_PHONE: /(\d{2})\d{5}(\d+)/,
  TAX_NUMBER: /\d{9,12}/,
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

export const TAX = {
  CO_NAME: {
    label: 'Tên công ty:',
    value: 'coName',
    placeholder: 'Nhập tên công ty',
  },
  CO_ADDRESS: {
    label: 'Địa chỉ công ty:',
    value: 'coAddress',
    placeholder: 'Nhập địa chỉ công ty',
  },
  BRANCH_NAME: {
    label: 'Chi nhánh (không bắt buộc):',
    value: 'coBranch',
    placeholder: 'Nhập tên chi nhánh',
  },
  TAX_NUMBER: {
    label: 'Mã số thuế:',
    value: 'taxNumber',
    placeholder: 'Nhập mã số thuế',
  },
  REGISTER_NUMBER: {
    label: 'Số đăng ký (không bắt buộc):',
    value: 'registerNumber',
    placeholder: 'Nhập số đăng ký',
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

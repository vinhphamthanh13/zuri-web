export const REGEXP = {
  PHONE_NUMBER: /^\d{10,11}$/,
  COUNTRY_CODE: /^\+\d{2,3}$/,
  VERIFY_CODE: /^\d{6}$/,
  ENCRYPT_PHONE: /(\d{2})\d{5}(\d+)/,
  TAX_NUMBER: /\d{9,12}/,
};

export const INPUT = {
  USER_NAME: {
    LABEL: 'Tên:',
    VALUE: 'userName',
    PLACEHOLDER: 'Vui lòng nhập tên',
  },
  LAST_NAME: {
    LABEL: 'Họ:',
    VALUE: 'lastName',
    PLACEHOLDER: 'Vui lòng nhập họ',
  },
  PHONE_NUMBER: {
    LABEL: 'Số điện thoại:',
    VALUE: 'phoneNumber',
    PLACEHOLDER: 'Vui lòng nhập số điện thoại',
  },
  SHOP_ADDRESS: {
    LABEL: 'Địa chỉ cửa hàng:',
    VALUE: 'shopAddress',
    PLACEHOLDER: 'Nhập địa chỉ cửa hàng',
  },
};

export const TAX = {
  CO_NAME: {
    LABEL: 'Tên công ty:',
    VALUE: 'coName',
    PLACEHOLDER: 'Nhập tên công ty',
  },
  CO_ADDRESS: {
    LABEL: 'Địa chỉ công ty:',
    VALUE: 'coAddress',
    PLACEHOLDER: 'Nhập địa chỉ công ty',
  },
  BRANCH_NAME: {
    LABEL: 'Chi nhánh (không bắt buộc):',
    VALUE: 'coBranch',
    PLACEHOLDER: 'Nhập tên chi nhánh',
  },
  TAX_NUMBER: {
    LABEL: 'Mã số thuế:',
    VALUE: 'taxNumber',
    PLACEHOLDER: 'Nhập mã số thuế',
  },
  REGISTER_NUMBER: {
    LABEL: 'Số đăng ký (không bắt buộc):',
    VALUE: 'registerNumber',
    PLACEHOLDER: 'Nhập số đăng ký',
  },
};

export const SHOP_DETAIL = {
  CATEGORY: {
    BUSINESS: {
      NAME: 'Cửa hàng & Loại sản phẩm',
      MENU: [
        {
          LABEL: 'Mô hình kinh doanh',
          VALUE: 'businessName',
          PLACEHOLDER: 'Chọn mô hình kinh doanh',
        },
        {
          LABEL: 'Doanh mục sản phẩm',
          VALUE: 'BusinessType',
          PLACEHOLDER: 'Chọn sản phẩm kinh doanh',
        },
      ],
    },
    INFO: {
      NAME: 'Thông tin cửa hàng',
      MENU: [
        {
          LABEL: 'Tên cửa hàng',
          VALUE: 'shopName',
          PLACEHOLDER: 'Nhập tên cửa hàng',
        },
        {
          LABEL: 'Số điện thoại',
          VALUE: 'phoneNumber',
          PLACEHOLDER: 'Nhập số điện thoại',
        },
        {
          LABEL: 'Địa chỉ',
          VALUE: 'shopAddress',
          PLACEHOLDER: 'Nhập địa chỉ cửa hàng',
        },
      ],
    },
    BRANCH: {
      NAME: 'Thông tin chi nhánh',
      MENU: [
        {
          NAME: 'Tên quản lý',
          VALUE: 'branchName',
          PLACEHOLDER: 'Nhập tên quản lý chi nhánh',
        },
        {
          NAME: 'Số điện thoại',
          VALUE: 'branchPhoneNumber',
          PLACEHOLDER: 'Nhập số điện thoại',
        },
        {
          NAME: 'Email',
          VALUE: 'branchEmail',
          PLACEHOLDER: 'Nhập địa chỉ email',
        },
      ],
    },
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

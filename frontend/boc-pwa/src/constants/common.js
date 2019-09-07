import { HTTP_STATUS } from 'constants/http';
import { tabsRoute } from 'routes';

export const G_CAPTCHA_ID = 'recaptcha-id';
export const GOOGLE_CAPTCHA_SECRET_KEY =
  '6Lc7J7YUAAAAABG41he8PrwxewS8ZzOAMZfok9XG';
export const GOOGLE_CAPTCHA_SITE_KEY =
  '6Lc7J7YUAAAAAOqthWot8Dz1k7io8lX3Hw1Qlvfh';
export const GOOGLE_CAPTCHA_SCRIPT = 'https://www.google.com/recaptcha/api.js';

/* Navigation */
export const LS_REGISTER = 'registerUser';
export const LS_CREATING_STORE = 'creatingStore';
export const BLOCKING_STORE_MESSAGE =
  'Bạn có muốn hủy đăng ký cửa hàng? Dữ liệu chưa lưu sẽ bị xóa và không thể khôi phục!';
export const BLOCKING_OTP_MESSAGE =
  'Bạn có muốn hủy xác thực OTP? Bạn sẽ cần gởi lại mã xác thực OTP mới nếu quay lại!';
// Layout
export const LAYOUT = {
  MAX_WIDTH: 768,
};
export const HEADER_TABS_HEIGHT = 90;

// Processing data

export const DATA = {
  ROOT: 'data',
  SUCCESS: 'data.success',
  MESSAGE: 'data.message',
  CODE: 'data.code',
  TOKEN: 'data.accessToken',
  OBJECT: 'data.object',
  OBJECTS: 'data.objects',
};
export const INIT_USER = {
  message: '',
  success: null,
  code: HTTP_STATUS.INTERNAL_ERROR,
};

// Regular expression

export const REGEXP = {
  PHONE_NUMBER: /^0\d{9}$/,
  COUNTRY_CODE: /^\+\d{2,3}$/,
  VERIFY_CODE: /^\d{4}$/,
  ENCRYPT_PHONE: /([\w+]{4})(\d+)(\d{2})$/,
  TAX_NUMBER: /\d{9,12}/,
  EMAIL: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
};

// Tabs and Page

export const REGISTER = {
  USER_NAME: {
    LABEL: 'Tên quản lý',
    VALUE: 'userName',
    PLACEHOLDER: 'Nhập tên quản lý',
  },
  SHOP_NAME: {
    LABEL: 'Tên cửa hàng',
    VALUE: 'shopName',
    PLACEHOLDER: 'Nhập tên cửa hàng',
  },
  SHOP_ADDRESS: {
    LABEL: 'Địa chỉ cửa hàng',
    VALUE: 'shopAddress',
    PLACEHOLDER: 'Nhập địa chỉ cửa hàng',
  },
  BUSINESS_TYPE: {
    LABEL: 'Mô hình kinh doanh',
    VALUE: 'businessType',
    PLACEHOLDER: 'Nhập mô hình kinh doanh',
  },
  CATEGORY_TYPE: {
    LABEL: 'Danh mục sản phẩm',
    VALUE: 'categoryType',
    PLACEHOLDER: 'Nhập danh mục sản phẩm',
  },
  EMAIL: {
    TYPE: 'email',
    LABEL: 'Email',
    VALUE: 'userEmail',
    PLACEHOLDER: 'Nhập địa chỉ email',
  },
  POLICIES: {
    TYPE: 'checkbox',
    LABEL: 'Đồng ý với chính sách và điều khoản',
    VALUE: 'policiesAndTerms',
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
    TYPE: 'tel',
    LABEL: 'Mã số thuế:',
    VALUE: 'taxNumber',
    PLACEHOLDER: 'Nhập mã số thuế',
  },
  REGISTER_NUMBER: {
    TYPE: 'tel',
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
          DROPDOWN: true,
        },
        {
          LABEL: 'Doanh mục sản phẩm',
          VALUE: 'BusinessType',
          PLACEHOLDER: 'Chọn sản phẩm kinh doanh',
          GUTTER: true,
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
          TYPE: 'tel',
          LABEL: 'Số điện thoại',
          VALUE: 'phoneNumber',
          PLACEHOLDER: 'Nhập số điện thoại',
        },
        {
          LABEL: 'Địa chỉ',
          VALUE: 'shopAddress',
          PLACEHOLDER: 'Nhập địa chỉ cửa hàng',
          GUTTER: true,
        },
      ],
    },
    BRANCH: {
      NAME: 'Thông tin chi nhánh',
      MENU: [
        {
          LABEL: 'Tên quản lý',
          VALUE: 'branchName',
          PLACEHOLDER: 'Nhập tên quản lý chi nhánh',
        },
        {
          TYPE: 'tel',
          LABEL: 'Số điện thoại',
          VALUE: 'branchPhoneNumber',
          PLACEHOLDER: 'Nhập số điện thoại',
        },
        {
          TYPE: 'email',
          LABEL: 'Email',
          VALUE: 'branchEmail',
          PLACEHOLDER: 'Nhập địa chỉ email',
          GUTTER: true,
        },
      ],
    },
  },
};
export const TABS = {
  GENERAL: {
    NAME: 'tổng quan',
    URL: `${tabsRoute.HOME}`,
  },
  REPORT: {
    NAME: 'báo cáo',
    URL: `${tabsRoute.REPORT}`,
  },
  ACTIVITY: {
    NAME: 'hoạt động',
    URL: `${tabsRoute.ACTIVITY}`,
  },
  SHOP: {
    NAME: 'cửa hàng',
    URL: `${tabsRoute.SHOP}`,
  },
};

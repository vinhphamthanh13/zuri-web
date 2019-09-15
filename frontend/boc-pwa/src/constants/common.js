import { HTTP_STATUS } from 'constants/http';
import { BubbleChart, ShowChart, Store, TrackChanges } from 'constants/svg';
import { tabsRoute } from 'routes';

// Google
export const G_CAPTCHA_ID = 'recaptcha-id';
export const GOOGLE_CAPTCHA_SECRET_KEY =
  '6Lc7J7YUAAAAABG41he8PrwxewS8ZzOAMZfok9XG';
export const GOOGLE_CAPTCHA_SITE_KEY =
  '6Lc7J7YUAAAAAOqthWot8Dz1k7io8lX3Hw1Qlvfh';
export const GOOGLE_CAPTCHA_SCRIPT = 'https://www.google.com/recaptcha/api.js';

/* Navigation */
export const LS_REGISTER = 'registerUser';
export const LS_CREATING_STORE = 'creatingStore';
export const LS_SHOP_ID = 'shopId';
export const LS_COME_BACK = 'comeBack';
export const CHANGE_ACTIVATION_PHONE = 'Thay đổi số điện thoại';
export const BLOCKING_STORE_MESSAGE = 'Bạn có muốn hủy đăng ký cửa hàng?';
export const BLOCKING_OTP_MESSAGE = 'Bạn có muốn hủy kích hoạt OTP?';
export const BLOCKING_TABS_MESSAGE =
  'Bạn có muốn thoát khỏi chương trình. Bạn sẽ cần đăng nhập lại để truy cập dữ liệu hiện hành.';
export const BLOCKING_LAYOUT_MESSAGE = 'Bạn có muốn thoát khỏi ứng dụng?';
export const RESEND_OTP_MESSAGE_SECURITY =
  'Vì lý do bảo mật. Vui lòng nhập số điện thoại';

// Layout
export const LAYOUT = {
  MAX_WIDTH: 768,
};
export const HEADER_HEIGHT = 50;
export const HEADER_HEIGHT_GUTTER = 34;
export const TABS_HEIGHT = 56;

// CTA label
export const OK = 'Đồng ý';
export const EDIT = 'Chỉnh sửa';
export const CANCEL = 'Hủy';
export const CLOSE = 'Đóng lại';
export const START = 'Bắt đầu';
export const SAVE = 'Lưu';
export const CHANGE_STORE = 'Đổi cửa hàng';
export const RESEND_OTP = 'Gởi lại mã OTP';

// CTA value
export const RESEND_OTP_TIMEOUT = 45 * 1000;

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
  SHOP_PHONE: {
    LABEL: 'Số điện thoại cửa hàng',
    VALUE: 'managerPhone',
    PLACEHOLDER: 'Số điện thoại liên hệ',
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
      NAME: 'Quy mô cửa hàng',
      MENU: [
        {
          LABEL: 'Mô hình kinh doanh',
          VALUE: 'businessType',
          PLACEHOLDER: 'Chọn mô hình kinh doanh',
          DROPDOWN: true,
        },
        {
          LABEL: 'Doanh mục sản phẩm',
          VALUE: 'categoryType',
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
          LABEL: 'Số điện thoại cửa hàng',
          VALUE: 'managerPhone',
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
          VALUE: 'userName',
          PLACEHOLDER: 'Nhập tên quản lý chi nhánh',
        },
        {
          TYPE: 'tel',
          LABEL: 'Số điện thoại quản lý',
          VALUE: 'phone',
          PLACEHOLDER: 'Nhập số điện thoại',
        },
        {
          TYPE: 'email',
          LABEL: 'Email',
          VALUE: 'userEmail',
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
export const TAB_ICONS = [TrackChanges, ShowChart, BubbleChart, Store];

// Messages
export const EMPTY_ITEM = 'Danh sách đang còn trống';
export const ACCESS_DENIED = 'Không có quyền truy cập vào trang này';

// API Backend properties

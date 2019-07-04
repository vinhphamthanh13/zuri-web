export const APP = {
  ERROR: 'internal_app_error',
  LOADED: 'internalr_app_loaded',
  LOADING: 'internal_app_loading',
};

export const APP_ALERT = {
  SUCCESS: 'internal_alert_success',
  ERROR: 'internal_alert_error',
  WARN: 'internal_alert_warn',
  CLEAR: 'internal_alert_clear',
};

export const AUTH = {
  LOGIN_FAILED: 'login_failed',
  LOGIN_SUCCESS: 'login_success',
  LOGOUT_SUCCESS: 'logout_success',
  GET_PROFILE_SUCCESS: 'get_profile_success',
};

export const SALE = {
  UPLOAD_SALES_FAILED: 'upload_sales_failed',
  UPLOAD_SALES_SUCCESS: 'upload_sales_success',
  UPLOAD_ERROR_CODE: {
    NO_CONTENT: 'Không có dữ liệu',
    WRONG_TYPE: 'Vui lòng tải lên tập tin csv hoặc excel',
    INVAILD_FORMAT: 'Dữ liệu không hợp lệ',
  },
};

export const DEFAULT_STATE = {
  error: {},
  loading: false,
  alert: {},
  auth: {
    user: {
      username: null,
      id: null,
      name: null,
      role: [],
    },
    token: null,
    tokenType: null,
    expiresIn: null,
  },
};

export const MESSAGE = {
  LOGIN_FAILED_INFO: 'Thông tin đăng nhập không hợp lệ, vui lòng kiểm tra lại!',
  UPLOAD_SALE_SUCCESS:
    'Tải lên thành công <%= numberImportedRecords %> dòng dữ liệu.',
  UPLOAD_SALE_FAILED: '',
  INTERNAL_ERROR: 'Lỗi hệ thống! vui lòng thử lại sau.',
  FILE_TOO_LARGE: 'File bạn đã chọn quá lớn. Kích thước tối đa là 5MB',
  UNAUTHORIZED: 'Vui lòng đăng nhập để tiếp tục',
  INVAILD_FORMAT: 'Dữ liệu không hợp lệ, vui lòng kiểm tra',
};

export const ERROR_UPLOAD_CODES = {
  INVAILD_FORMAT: 1001,
};

export const USERNAME_PLACEHOLDER = 'Tên đăng nhập';
export const PASSWORD_PLACEHOLDER = 'Mật khẩu';
export const LOGIN_TITLE = 'Thông tin đăng nhập';
export const LOGIN_BUTTON = 'Đăng nhập';
export const HEADER_TITLE = 'Dành cho SA tại BOCVN';
export const PAGE_TITLE = 'Đăng nhập quản trị ứng dụng';
export const INPUT_PLACEHOLDER = 'Nhập số điện thoại nhận OTP';
export const PHONE_PALCEHOLDER = 'Nhập mã xác thực (OTP)';
export const MESSAGE_OVER_RETRY =
  'Vui lòng nhập lại số điện thoại liên lạc để xác thực.';
export const MESSAGE_VERIFY_FAIL =
  'Không xác nhận được OTP, vui lòng thử lại sau!';
export const MESSAGE_VERIFY_WRONG =
  'Mã xác thực bạn vừa nhập không chính xác. Vui lòng nhập lại';
export const MAX_RETRY = 3;
export const MAX_TIME_RESEND_OTP = 3;
export const SECOND_COUNTDOWN = 300;
export const MATCHED = 'MATCHED';
export const MESSAGE_RESEND_REMAIN = `Mã xác thực OTP không đúng`;
export const MESSAGE_RETRY_OTP = ['Bạn còn', 'lần Gửi lại Mã Xác Thực'];
export const NUMBER_REMAIN = number => `0${number}`;
export const MESSAGE_ALREADY_REGISTER =
  'Đăng ký thiết bị không thành công vì bạn đã đăng ký trên thiết bị khác';
export const MESSAGE_OTP_WRONG = {
  resend: 'Xin vui lòng nhập lại. Bạn còn',
  times: 'lần nhập.',
};
export const MESSAGE_OUT_OF_TIME = {
  overtry: 'Bạn vừa nhập sai Mã Xác Thực quá',
  times: 'lần.',
  maxTime: NUMBER_REMAIN(3),
};

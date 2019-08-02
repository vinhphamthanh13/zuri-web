import * as Yup from 'yup';
import { REGEXP } from 'constants/common';

export const activation = Yup.object().shape({
  countryCode: Yup.string()
    .required('* Nhập mã quốc gia')
    .matches(REGEXP.COUNTRY_CODE, {
      message: 'Mã quốc gia không hợp lệ.',
      excludeEmptyString: true,
    }),
  phoneNumber: Yup.string()
    .required('* Nhập số điện thoại')
    .matches(REGEXP.PHONE_NUMBER, {
      message: 'Số điện thoại không hợp lệ.',
      excludeEmptyString: true,
    }),
});

export const register = Yup.object().shape({
  userName: Yup.string().required('* Nhập tên người dùng'),
  lastName: Yup.string().required('* Nhập họ, tên đệm'),
  phoneNumber: Yup.string()
    .required('* Nhập số điện thoại')
    .matches(REGEXP.PHONE_NUMBER, {
      message: 'Số điện thoại không hợp lệ.',
      excludeEmptyString: true,
    }),
  shopAddress: Yup.string().required('* Nhập địa chỉ cửa hàng'),
});

export const verifyCode = Yup.object().shape({
  verifyCode: Yup.string()
    .required('* Nhập mã xác minh')
    .matches(REGEXP.VERIFY_CODE, { message: 'Mã xác minh không hợp lệ' }),
});

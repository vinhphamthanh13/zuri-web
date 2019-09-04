import * as Yup from 'yup';
import { REGEXP } from 'constants/common';

export const activation = Yup.object().shape({
  countryCode: Yup.string()
    .required('* Nhập mã quốc gia')
    .matches(REGEXP.COUNTRY_CODE, {
      message: '* Mã quốc gia không hợp lệ.',
    }),
  phoneNumber: Yup.string()
    .required('* Nhập số điện thoại')
    .matches(REGEXP.PHONE_NUMBER, {
      message: '* Số điện thoại không hợp lệ.',
    }),
});

export const register = Yup.object().shape({
  userName: Yup.string()
    .matches(/[\w]{2,}/, {
      message: '* Tên phải có ít nhất 2 ký tự',
    })
    .required('* Nhập tên người dùng'),
  shopName: Yup.string()
    .matches(/[\w]{2,}/, {
      message: '* Tên phải có ít nhất 2 ký tự',
    })
    .required('* Nhập tên cửa hàng'),
  shopAddress: Yup.string()
    .matches(/[\w\W]{10,}/, {
      message: '* Địa chỉ không hợp lệ',
    })
    .required('* Nhập địa chỉ cửa hàng'),
  businessType: Yup.string().required('* Chọn mô hình kinh doanh'),
  categoryType: Yup.string().required('* Chọn danh mục sản phẩm'),
  userEmail: Yup.string().matches(REGEXP.EMAIL, {
    message: '* Địa chỉ email không hợp lệ',
  }),
  policiesAndTerms: Yup.bool().oneOf([true], 'Chọn'),
});

export const registerTax = Yup.object().shape({
  coName: Yup.string()
    .matches(/[\w]{2,}/, {
      message: '* Tên phải có ít nhất 2 ký tự',
    })
    .required('* Nhập tên công ty'),
  coAddress: Yup.string()
    .matches(/[\w\s]{10,}/, {
      message: '* Địa chỉ không hợp lệ',
    })
    .required('* Nhập địa chỉ công ty'),
  coBranch: Yup.string().matches(/[\w]{3,}/, {
    message: '* Tên chi nhánh có ít nhất 3 ký tự',
  }),
  taxNumber: Yup.string()
    .required('* Nhập số mã số thuế')
    .matches(REGEXP.TAX_NUMBER, {
      message: '* Mã số thuế không hợp lệ.',
      excludeEmptyString: true,
    }),
  registerNumber: Yup.string().matches(REGEXP.PHONE_NUMBER, {
    message: '* Số đăng ký không hợp lệ.',
    excludeEmptyString: true,
  }),
});

export const otpCode = Yup.object().shape({
  verifyCode: Yup.string()
    .required('* Nhập mã xác minh')
    .matches(REGEXP.VERIFY_CODE, { message: '* Mã xác minh không hợp lệ' }),
});

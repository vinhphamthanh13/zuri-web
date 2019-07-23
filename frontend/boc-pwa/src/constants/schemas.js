import * as Yup from 'yup';
import { REGEXP } from 'constants/common';

export const activation = Yup.object().shape({
  countryCode: Yup.string()
    .required('Nhập mã quốc gia')
    .matches(REGEXP.COUNTRY_CODE, {
      message: 'Mã quốc gia không hợp lệ.',
      excludeEmptyString: true,
    }),
  phoneNumber: Yup.string()
    .required('Nhập số điện thoại')
    .matches(REGEXP.PHONE_NUMBER, {
      message: 'Số điện thoại không hợp lệ.',
      excludeEmptyString: true,
    }),
});

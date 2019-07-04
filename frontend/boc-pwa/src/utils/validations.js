/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import * as Yup from 'yup';
import { pick, head, isEmpty } from 'lodash';

const REQUIRED_FIELD_MESSAGE = 'Thông tin này là bắt buộc!';
export const DOB_VALIDATION_MESSAGE = 'Ngày sinh không hợp lệ';
export const REQUIRED_DOB_INPUT = 'Vui lòng nhập ngày sinh';
const INVALID_FIELD_MESSAGE = 'Thông tin này không hợp lệ';

const REQUIRED_FIELD_LAST_NAME = 'Vui lòng nhập họ';
const INVALID_FIELD_LAST_NAME = 'Họ không hợp lệ';

const INVALID_FIELD_MIDDLE_NAME = 'Tên đệm không hợp lệ';

const REQUIRED_FIELD_FIRST_NAME = 'Vui lòng nhập tên';
const INVALID_FIELD_FIRST_NAME = 'Tên không hợp lệ';

const REQUIRED_FIELD_IMG = 'Vui lòng chụp hình cá nhân';
const REQUIRED_PHONE_NUMBER = 'Vui lòng nhập số điện thoại';
const REQUIRED_OTP_CODE = 'Vui lòng nhập mã xác thực';
const REQUIRED_INCOME_INPUT = 'Vui lòng nhập thu nhập hàng tháng';
const REQUIRED_DRIVING_LICENSE = 'Vui lòng nhập số giấy phép lái xe';
const REQUIRED_FAMILY_BOOK_NUMBER = 'Vui lòng nhập số sổ hộ khẩu';
const REQUIRED_ID_NUMBER_FIELD = 'Vui lòng nhập số CMND/CCCD';

const REQUIRED_SA_USERNAME = 'Vui lòng nhập SA code';
const SA_PASSWORD = 'Vui lòng nhập password';

const IS_VALID_STRING = /^([^0-9!@#$%^&*(){}[\]~_+=\-•£€¥¢®¿§×¶°¬¦|¿¡÷;°":;<>?,]*)$/;
const IS_HAS_NUMBER = /^[0-9]+$/;

const validationSchema = {
  firstName: Yup.string()
    .required(REQUIRED_FIELD_FIRST_NAME)
    .matches(IS_VALID_STRING, INVALID_FIELD_FIRST_NAME),
  middleName: Yup.string().matches(IS_VALID_STRING, INVALID_FIELD_MIDDLE_NAME),
  lastName: Yup.string()
    .required(REQUIRED_FIELD_LAST_NAME)
    .matches(IS_VALID_STRING, INVALID_FIELD_LAST_NAME),
  dayOfBirth: Yup.date()
    .nullable()
    .required(REQUIRED_DOB_INPUT),
  imageUrl: Yup.object()
    .required(REQUIRED_FIELD_MESSAGE)
    .test('required', REQUIRED_FIELD_MESSAGE, val => !isEmpty(val)),
  idCard: Yup.string()
    .required(REQUIRED_ID_NUMBER_FIELD)
    .test(
      'len',
      'Số CMND/CCCD không hợp lệ',
      val => val && (val.length >= 3 && val.length <= 12),
    ),
  monthlyIncome: Yup.string().required(REQUIRED_INCOME_INPUT),
  monthlyPayment: Yup.string(),
  identificationType: Yup.string().required(REQUIRED_FIELD_MESSAGE),
  phoneNumber: Yup.string()
    .required(REQUIRED_PHONE_NUMBER)
    .matches(IS_HAS_NUMBER, INVALID_FIELD_MESSAGE)
    .test('len', 'Số điện thoại không hợp lệ', val => val && head(val) === '0')
    .test('len', 'Số điện thoại không hợp lệ', val => val && val.length === 10),
  drivingLicence: Yup.string().when('identificationType', {
    is: val => val === 'drivingLicence',
    then: Yup.string().required(REQUIRED_DRIVING_LICENSE),
  }),
  familyBook: Yup.string().when('identificationType', {
    is: val => val === 'familyBook',
    then: Yup.string().required(REQUIRED_FAMILY_BOOK_NUMBER),
  }),
  email: Yup.string().email('Sai định dạng email'),
  otp: Yup.string()
    .required(REQUIRED_OTP_CODE)
    .test(
      'len',
      'Mã xác thực không đúng',
      val => val && !isEmpty(val) && (val.length === 6 || val.length === 8),
    ),
  acceptedTerm: Yup.boolean()
    .required('Vui lòng đồng ý để tiếp tục')
    .oneOf([true], 'Vui lòng đồng ý để tiếp tục'),
  userImage: Yup.object()
    .required(REQUIRED_FIELD_IMG)
    .test('len', REQUIRED_FIELD_IMG, val => !isEmpty(val)),
  saCode: Yup.string().required(REQUIRED_FIELD_MESSAGE),
  username: Yup.string().required(REQUIRED_FIELD_MESSAGE),
  password: Yup.string().required(REQUIRED_FIELD_MESSAGE),
  filename: Yup.string().required(REQUIRED_FIELD_MESSAGE),
  saUsername: Yup.string().required(REQUIRED_SA_USERNAME),
  saPassword: Yup.string().required(SA_PASSWORD),
};

const extractFields = fields => pick(validationSchema, fields);
const getValidationSchema = values => Yup.object().shape(extractFields(values));

export { validationSchema, getValidationSchema };

/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
import moment from 'moment';
import { Input, NumberInput, MultipleImageInput } from 'homecredit-ui';
import { REQUIRED_DOB_INPUT, DOB_VALIDATION_MESSAGE } from 'utils/validations';
import DatePicker from 'components/common/DatePicker';
import Welcome from './Welcome';
import GenderSelect from './GenderSelect';
import InputPhone from './InputPhone';
import OTP from './OTP';
import CalculateCash from './CalculateCash';
import UserInfo from './UserInfo';
import UserIncome from './UserIncome';
import Summary from './Summary';
import Congratulation from './Congratulation';

import sohokhau from './images/sohokhau.png';
import chuho from './images/chuho.png';
import giaypheplaixe from './images/giaypheplaixe.png';
import imagecapture from './images/imagecapture.png';

export const TITLE_1BOD = 'Vay tiền mặt';

export const REGEX = {
  NONE_SPACE: /^\S+$/,
  NONE_SPECIAL_CHARACTER: /[^a-zA-Z0-9]/,
};

export const STEPS = {
  WELCOME_PAGE: 0,
  CALCULATE_CASH: 1,
  INPUT_PHONE: 2,
  OTP: 3,
  USER_INFO: 4,
  SOCIAL_INFO: 5,
  SUMMARY: 6,
  CONGRATULATION: 7,
};

export const STEP_NAME = {
  [STEPS.WELCOME_PAGE]: 'welcome',
  [STEPS.CALCULATE_CASH]: 'loan-offer',
  [STEPS.INPUT_PHONE]: 'input-phone',
  [STEPS.OTP]: 'input-OTP',
  [STEPS.USER_INFO]: 'personal-info',
  [STEPS.SOCIAL_INFO]: 'income-info',
  [STEPS.SUMMARY]: 'summary',
  [STEPS.CONGRATULATION]: 'submit-result',
};

export const UNIFIED_ID_TYPE = {
  familyBook: 'drivingLicence',
  drivingLicence: 'familyBook',
};

export const HEADER_TITLES = {
  [STEPS.INPUT_PHONE]: 'Nhập số điện thoại của bạn',
  [STEPS.OTP]: 'Nhập Mã Xác Thực (OTP)',
  [STEPS.CALCULATE_CASH]: 'Hãy chọn nhu cầu vay của bạn!',
  [STEPS.USER_INFO]: 'Hãy đăng ký thông tin cá nhân của bạn!',
  [STEPS.SOCIAL_INFO]: 'Hãy đăng ký thông tin cá nhân của bạn!',
  [STEPS.SUMMARY]: 'Cùng xem lại thông tin bạn đã đăng ký!',
};

export const STEP_COMPONENT = {
  [STEPS.WELCOME_PAGE]: Welcome,
  [STEPS.CALCULATE_CASH]: CalculateCash,
  [STEPS.INPUT_PHONE]: InputPhone,
  [STEPS.OTP]: OTP,
  [STEPS.USER_INFO]: UserInfo,
  [STEPS.SOCIAL_INFO]: UserIncome,
  [STEPS.SUMMARY]: Summary,
  [STEPS.CONGRATULATION]: Congratulation,
};

export const GENDER = {
  MALE: 'Nam',
  FEMALE: 'Nữ',
};

export const NEXT_TEXT = 'Tiếp tục';

// ----- FIRST BOD ------
export const FIRST_BOD_URL = '/1bod/';

// ----- INPUT PHONE ------
export const INPUT_PHONE_FIELDS = {
  label: 'Nhập số điện thoại liên lạc',
  name: 'phoneNumber',
  type: 'tel',
};
export const INPUT_PHONE_TEXTS = {
  TITLE: 'Mã xác thực (OTP) sẽ được gửi đến số điện thoại này',
};

// ----- SA_CODE ------
export const SA_CODE_INPUT = {
  label: 'Mã giới thiệu',
  name: 'retailAgent',
  type: 'text',
};

// ----- OTP ------
export const OTP_TEXTS = {
  TERM: 'Bằng cách tiếp tục, tôi đã đồng ý những ',
  CONDITION: 'điều khoản và điều kiện',
  AGREE: 'Tôi đã hiểu',
  DESCRIPTION: 'Vui lòng nhập Mã Xác Thực được gửi đến số điện thoại ',
  MAXIMUM_TRIED_1: 'Bạn đã dùng hết số lần gửi Mã Xác Thực.',
  MAXIMUM_TRIED_2: 'Vui lòng nhập lại số điện thoại liên lạc để xác thực.',
  ATTEMP_1: 'Mã Xác Thực vừa nhập không đúng',
  ATTEMP_2: 'Xin vui lòng nhập lại. Bạn còn ',
  ATTEMP_3: ' lần nhập.',
  MAXIMUM_ATTEMP_1: 'Bạn vừa nhập sai Mã Xác Thực quá ',
  MAXIMUM_ATTEMP_2: ' lần.',
  MAXIMUM_ATTEMP_3: 'Vui lòng nhập lại số điện thoại liên lạc để xác thực.',
  OTP_DISABLED:
    'Mã Xác Thực đã hết hạn. Vui lòng nhấn "Gửi lại Mã Xác Thực" đế lấy Mã Xác Thực mới',
  TERM_CONDITION:
    'Tôi đồng ý cho phép http://www.bocvietnam.com thu nhập, sử dụng cũng như chia sẻ với bên thứ ba các thông tin liên quan đến tôi và/ hoặc liên quan đến các khoản vay của tôi.',
};
export const INPUT_OTP_FIELDS = {
  label: 'Nhập Mã Xác Thực',
  name: 'otp',
  type: 'tel',
};
export const ACCEPTED_TERM_FIELDS = {
  label: 'Bằng cách tiếp tục, tôi đồng ý những ',
  name: 'acceptedTerm',
};
export const MAX_ATTEMPT = 3;
export const MAX_RETRY = 3;
export const OTP_COUNT_SECOND = 300;

// ----- CALCULATE CASH ------
export const CREDIT_AMOUNT_FIELDS = {
  label: 'Số tiền vay',
  name: 'creditAmount',
  type: 'number',
};
export const TENOR_FIELDS = {
  label: 'Thời gian vay',
  name: 'tenor',
  type: 'number',
};
export const CREDIT_AMOUNT_MIN = 10;
export const CREDIT_AMOUNT_MAX = 40;
export const CREDIT_AMOUNT_STEP = 5;
export const TENOR_MIN = 12;
export const TENOR_MAX = 48;

export const TENOR_BUTTONS = [
  { value: 12, content: '12' },
  { value: 18, content: '18' },
  { value: 24, content: '24' },
  { value: 36, content: '36' },
  { value: 48, content: '48' },
];

// ----- USER INFO ------
export const USER_INFO_TEXTS = {
  NOTE: 'Vui lòng điền đầy đủ dấu và các thông tin bắt buộc (*)',
  BIRTHDAY_POPUP_1: 'Vui lòng nhập ngày 01 nếu chỉ có tháng & năm sinh.',
  BIRTHDAY_POPUP_2: 'Vui lòng nhập ngày 01, tháng 01 nếu chỉ có năm sinh.',
};

export const USER_FORM_FIELDS = [
  {
    label: 'Họ',
    name: 'lastName',
    maxLength: 30,
    component: Input,
    icon: 'create',
    require: true,
    trackingLabel: 'Last_name',
    trackingAction: 'Input_last_name',
    className: 'col-12',
    msgPosition: 'below',
  },
  {
    label: 'Tên đệm (Tên lót)',
    name: 'middleName',
    maxLength: 30,
    component: Input,
    icon: 'create',
    trackingLabel: 'Middle_name',
    trackingAction: 'Input_middle_name',
    className: 'col-12',
    msgPosition: 'below',
  },
  {
    label: 'Tên',
    name: 'firstName',
    maxLength: 30,
    component: Input,
    icon: 'create',
    require: true,
    trackingLabel: 'First_name',
    trackingAction: 'Input_first_name',
    className: 'col-12',
    msgPosition: 'below',
  },
  {
    label: 'Ngày tháng năm sinh',
    name: 'dayOfBirth',
    component: DatePicker,
    trackingLabel: 'DOB',
    trackingAction: 'Select_DOB',
    maxDate: moment()
      .set({
        hour: 0,
        minute: 0,
        second: 0,
        millisecond: 0,
      })
      .subtract(20, 'y'),
    minDate: moment()
      .set({ hour: 0, minute: 0, second: 0, millisecond: 0 })
      .subtract(60, 'y'),
    modalContent: {
      trackingLabel: 'DOB_instruction',
      trackingAction: 'View_DOB_instruction',
      title: 'Lưu ý',
      contents: [
        USER_INFO_TEXTS.BIRTHDAY_POPUP_1,
        USER_INFO_TEXTS.BIRTHDAY_POPUP_2,
      ],
    },
    className: 'col-12',
    validationMsg: DOB_VALIDATION_MESSAGE,
    requiredMsg: REQUIRED_DOB_INPUT,
    dateFormat: 'YYYY-MM-DD',
    displayFormat: 'DD / MM / YYYY',
    require: true,
  },
  {
    label: 'Giới tính',
    name: 'gender',
    component: GenderSelect,
    icon: true,
    trackingLabel: 'Gender',
    trackingAction: 'Select_Gender',
    className: 'col-12',
    msgPosition: 'below',
  },
  {
    label: 'Chụp hình cá nhân',
    name: 'userImage',
    component: MultipleImageInput,
    id: 'faceimage',
    required: true,
    min: 1,
    max: 1,
    require: true,
    modalContent: {
      trackingLabel: 'Selfie_instruction',
      trackingAction: 'View_selfie_instruction',
      title: 'Hướng dẫn chụp ảnh',
      image: imagecapture,
      contents: [
        '1. Mắt nhìn thẳng màn hình/ ống kính và giữ vẻ mặt tự nhiên',
        '2. Canh chỉnh khuôn mặt của quý khách ở giữa màn hình',
        '3. Ảnh màu và không bị đổ bóng',
        '4. Đảm bảo mặt không bị che, không đeo mắt kiếng hoặc đội mũ',
        '5. Không có người nào khác ngoài bạn trong ảnh',
      ],
    },
    trackingLabel: 'Taking_selfie',
    trackingAction: 'Select_taking_selfie',
    className: 'col-12',
    msgPosition: 'below',
  },
];
// ----- USER INCOME ------
export const POPUP_TYPE = {
  DL: 'Giấy phép lái xe',
  FRB: 'Sổ hộ khẩu',
};

export const POPUP_CONTENT = {
  [POPUP_TYPE.DL]: [
    {
      step: 1,
      image: giaypheplaixe,
      content: 'Vui lòng nhập tất cả ký tự bao gồm ',
      contentBold: 'chữ và số ',
      contentNext: 'trên giấy phép lái xe',
    },
  ],
  [POPUP_TYPE.FRB]: [
    {
      step: 1,
      title: 'Sổ hộ khẩu cũ (số CMND của chủ hộ nằm trên trang Chủ hộ)',
      image: chuho,
      content:
        'Vui lòng nhập số CMND của chủ hộ (hoặc số CMND của người tiếp theo trong sổ hộ khẩu nếu Chủ hộ không có số CMND)',
    },
    {
      step: 2,
      title: 'Sổ hộ khẩu mới (trang Chủ Hộ không có thông tin CMND của chủ hộ)',
      image: sohokhau,
      content: 'Vui lòng nhập số Sổ hộ khẩu trên trang đầu tiên của Sổ hộ khẩu',
    },
  ],
};

export const USER_INCOME_FIELDS = [
  {
    label: 'Khoản thu nhập hàng tháng (VNĐ)',
    name: 'monthlyIncome',
    thousandSeparator: '.',
    decimalSeperator: false,
    maxLength: 9,
    component: NumberInput,
    require: true,
    trackingLabel: 'Monthly_income',
    trackingAction: 'Input_monthly_income',
    type: 'tel',
    className: 'col-12',
    msgPosition: 'below',
  },
  {
    label: 'Khoản trả góp hàng tháng khác (VNĐ)',
    name: 'monthlyPayment',
    thousandSeparator: '.',
    decimalSeperator: false,
    maxLength: 9,
    component: NumberInput,
    trackingLabel: 'Other_monthly_installment',
    trackingAction: 'Input_other_monthly_installment',
    type: 'tel',
    className: 'col-12',
    msgPosition: 'below',
  },
  {
    label: 'Số CMND/ Căn cước công dân',
    name: 'idCard',
    decimalSeperator: false,
    maxLength: 12,
    component: Input,
    require: true,
    trackingLabel: 'Personal_ID',
    trackingAction: 'Input_personal_ID',
    className: 'col-12',
    msgPosition: 'below',
  },
];

// ----- SUMMARY ------
export const SUMMARY_TEXTS = {
  INSURANCE_AMOUNT: 'Phí bảo hiểm',
  MONTHTLY_FEE: 'Số tiền trả góp hàng tháng',
  FULL_NAME: 'Họ và tên',
  GENDER: 'Giới tính',
  DATEOFBIRTH: 'Ngày tháng năm sinh',
  ID_CARD: 'Số CMND/ Căn cước công dân',
  AGREE: 'Tôi đã hiểu',
  DECLINED: 'Tôi không muốn đăng ký bảo hiểm',
  ADD_INSURANCE: 'Tôi muốn đăng ký bảo hiểm',
  INSURANCE_TITLE: 'Lợi ích của Bảo hiểm khoản vay',
  INSURANCE_CONTENT_1: '1. Giúp hồ sơ vay dễ được phê duyệt.',
  INSURANCE_CONTENT_2:
    '2. Hỗ trợ chi trả khoản vay khi xảy ra các rủi ro không mong muốn như tai nạn dẫn đến mất khả năng lao động hoặc tử vong.',
  INSURANCE_CONTENT_3: '3. Bồi thường lên đến 250% giá trị khoản vay.',
  SA_POPUP_TITLE: 'Lưu ý',
  SA_POPUP_1:
    'Thông tin không hợp lệ có thể ảnh hưởng đến việc đăng ký đơn vay',
  SA_POPUP_2:
    'Vui lòng chỉ nhập thông tin này nếu bạn là nhân viên http://www.bocvietnam.com hoặc được nhân viên http://www.bocvietnam.com hướng dẫn',
  SA_CANCEL_BTN: 'Bỏ qua',
  SA_ACCEPT_BTN: 'Nhập thông tin',
};

// ----- CONGRATULATION ------

export const CONGRATS = {
  TITLE: 'Vay tiền mặt',
  SUMMARY:
    'Yêu cầu vay của bạn đã được đăng ký thành công. Chúng tôi sẽ thông báo kết quả đến bạn trong 15 phút tới',
};

// ----- MODALS ------

export const SUBMIT_APPLICATION_FAIL = 'SUBMIT_APPLICATION_FAIL';
export const AGREE = 'Tôi đã hiểu';
export const INVALID_SA_CODE = 'INVALID_SA_CODE';

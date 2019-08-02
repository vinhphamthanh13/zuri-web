/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

export const REGEXP = {
  PHONE_NUMBER: /^\d{10,11}$/,
  COUNTRY_CODE: /^\+\d{2,3}$/,
  VERIFY_CODE: /^\d{6}$/,
  ENCRYPT_PHONE: /(\d{2})\d{5}(\d+)/,
};

export const INPUT = {
  USER_NAME: {
    label: 'Tên:',
    value: 'userName',
    placeholder: 'Vui lòng nhập tên',
  },
  LAST_NAME: {
    label: 'Họ:',
    value: 'lastName',
    placeholder: 'Vui lòng nhập họ',
  },
  PHONE_NUMBER: {
    label: 'Số điện thoại:',
    value: 'phoneNumber',
    placeholder: 'Vui lòng nhập số điện thoại',
  },
  SHOP_ADDRESS: {
    label: 'Địa chỉ cửa hàng:',
    value: 'shopAddress',
    placeholder: 'Nhập địa chỉ cửa hàng',
  },
};

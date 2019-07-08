/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

export const EMPTY_FUNCTION = () => {};
export const FORM_STATUS = {
  INITIAL: 0,
  NEED_VALIDATE: 1,
  ERROR: 2,
  SUCCESS: 3,
};

export const LOADER_STATUS = {
  ON: true,
  OFF: false,
};

export const UPDATE_TOKEN = 'UPDATE_TOKEN';

export const MESSAGES = {
  GLOBAL_ERROR: 'Hệ thống đang được bảo trì. Quý khách vui lòng quay lại sau.',
  OK: 'OK',
};

export const CONTENT_TYPES = {
  MULTIPART_FORM: 'MULTIPART/FORM-DATA',
  JSON: 'application/json',
};

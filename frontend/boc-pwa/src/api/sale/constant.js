export const ERROR_CODES = {
  wrongType: { code: 'WRONG_TYPE', message: 'Wrong file type' },
  inValidFormat: { code: 'INVAILD_FORMAT', message: 'Format file is invaild' },
  noContent: { code: 'NO_CONTENT', message: 'no content' },
  invadidHeader: {
    code: 'INVALID_HEADER',
    message: 'Headers are not matching',
  },
  validatationFailed: {
    code: 'validatationFailed',
    message: 'Sale code is required',
  },
};

export const UPLOAD_STATUS = {
  failed: 'Failed',
  success: 'Success',
};

export const SALE_HEADERS = [
  'SHOP_CODE',
  'SHOP_NAME',
  'SHOP_ADDRESS',
  'SHOP_DISTRICT',
  'SHOP_PROVINCE',
  'SHOP_AREA',
  'DSM_MANAGE_NAME',
  'DSM_MAIL',
  'SA_CODE',
  'SA_NAME',
  'SA_MAIL',
  'SA_PHONE',
];

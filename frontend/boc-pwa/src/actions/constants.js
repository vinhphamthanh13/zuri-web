// Node Server

export const PROXY_AUTH = '/auth';
export const PROXY_SHOP = '/shop';
export const SEND_OTP_PARAMS = '/:countryCode/:phoneNumber';
export const VERIFY_OTP_PARAMS = `${SEND_OTP_PARAMS}/:otpCode`;

export const NODE_SERVER_URL = {
  ACTIVATION: '/activation',
  SENDING_OTP: '/sendingBOCOTP',
  VERIFYING_OTP: '/verifyingBOCOTP',
  CREATING_USER: '/creatingBOCUser',
  CREATING_STORE: '/creatingBOCStore',
  GETTING_STORE: '/gettingBOCStore',
  UPDATING_STORE: '/updatingBOCStore',
  EXISTING_USER: '/existingBOCUser',
};

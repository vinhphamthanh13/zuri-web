// Node Server

const OTP_PARAMS = '/:countryCode/:phoneNumber';

export const NODE_SERVER_URL = {
  AUTH: {
    ROOT: '/auth',
    ACTIVATION: '/activation',
    SENDING_OTP: `/sendingBOCOTP/${OTP_PARAMS}`,
    VERIFYING_OTP: `/verifyingBOCOTP/${OTP_PARAMS}/:otpCode`,
    CREATING_USER: '/creatingBOCUser',
    CREATING_STORE: '/creatingBOCStore',
    EXISTING_USER: '/existingBOCUser',
  },
};

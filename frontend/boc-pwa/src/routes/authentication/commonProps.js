import {
  nodeUsersApi,
  setPhoneNumberAction,
  getVerificationCodeAction,
  nodeVerificationCodeApi,
  nodeExistingUserApi,
} from 'actions/authenticationActions';

export const activationProps = {
  mapStateToProps: ({ authentication }) => ({
    phoneNumber: authentication.phoneNumber,
    getVerificationCodeStatus: authentication.getVerificationCodeStatus,
    existingUser: authentication.existingUser,
  }),
  mapDispatchToProps: dispatch => ({
    dispatchUsers: () => dispatch(nodeUsersApi()),
    dispatchSetPhoneNumber: number => dispatch(setPhoneNumberAction(number)),
    dispatchVerificationCode: (countryCode, phoneNumber) =>
      dispatch(nodeVerificationCodeApi(countryCode, phoneNumber)),
    dispatchExistingUser: phone => dispatch(nodeExistingUserApi(phone)),
  }),
};

export const verifyCodeProps = {
  mapStateToProps: ({ authentication }) => ({
    phoneNumber: authentication.phoneNumber,
  }),
  mapDispatchToProps: dispatch => ({
    clearVerificationCodeStatus: () =>
      dispatch(getVerificationCodeAction(false)),
  }),
};

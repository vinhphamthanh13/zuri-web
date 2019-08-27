import {
  nodeUsersApi,
  setPhoneNumberAction,
  verificationCodeAction,
  nodeVerificationCodeApi,
} from 'actions/authenticationActions';

export const activationProps = {
  mapStateToProps: ({ authentication }) => ({
    phoneNumber: authentication.phoneNumber,
    verificationCodeStatus: authentication.verificationCodeStatus,
  }),
  mapDispatchToProps: dispatch => ({
    dispatchUsers: () => dispatch(nodeUsersApi()),
    dispatchSetPhoneNumber: number => dispatch(setPhoneNumberAction(number)),
    dispatchVerificationCode: (countryCode, phoneNumber) =>
      dispatch(nodeVerificationCodeApi(countryCode, phoneNumber)),
  }),
};

export const verifyCodeProps = {
  mapDispatchToProps: dispatch => ({
    clearVerificationCodeStatus: () => dispatch(verificationCodeAction(false)),
  }),
};

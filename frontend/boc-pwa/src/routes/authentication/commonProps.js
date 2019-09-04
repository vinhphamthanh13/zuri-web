import { setError } from 'actions/common';
import {
  setPhoneNumberAction,
  sendingOTPAction,
  nodeSendingOTPApi,
  nodeVerifyingOTPApi,
  nodeExistingUserApi,
  existingUserAction,
  nodeCreatingUserApi,
  creatingUserAction,
} from 'actions/authenticationActions';

export const activationProps = {
  mapStateToProps: ({ authentication }) => ({
    phoneNumber: authentication.phoneNumber,
    encryptPhone: authentication.encryptPhone,
    getVerificationCodeStatus: authentication.getVerificationCodeStatus,
    existingUser: authentication.existingUser,
    creatingUser: authentication.creatingUser,
  }),
  mapDispatchToProps: dispatch => ({
    dispatchError: message => dispatch(setError(message)),
    dispatchSetPhoneNumber: number => dispatch(setPhoneNumberAction(number)),
    dispatchSendOTP: (countryCode, phoneNumber) =>
      dispatch(nodeSendingOTPApi(countryCode, phoneNumber)),
    dispatchExistingUser: phone => dispatch(nodeExistingUserApi(phone)),
    dispatchExistingUserAction: data => dispatch(existingUserAction(data)),
    dispatchCreatingUser: phone => dispatch(nodeCreatingUserApi(phone)),
  }),
};

export const verifyCodeProps = {
  mapStateToProps: ({ authentication }) => ({
    countryCode: authentication.countryCode,
    phoneNumber: authentication.phoneNumber,
    encryptPhone: authentication.encryptPhone,
    verifyingOTPStatus: authentication.verifyingOTPStatus,
  }),
  mapDispatchToProps: dispatch => ({
    clearOTPStatus: () => dispatch(sendingOTPAction(false)),
    dispatchVerifyOTPCode: (countryCode, phoneNumber, otpCode) =>
      dispatch(nodeVerifyingOTPApi(countryCode, phoneNumber, otpCode)),
  }),
};

export const creatingStoreProps = {
  mapStateToProps: ({ authentication }) => ({
    phoneNumber: authentication.phoneNumber,
    creatingUser: authentication.creatingUser,
  }),
  mapDispatchToProps: dispatch => ({
    dispatchExistingUserAction: data => dispatch(existingUserAction(data)),
    dispatchCreatingUserAction: value => dispatch(creatingUserAction(value)),
  }),
};

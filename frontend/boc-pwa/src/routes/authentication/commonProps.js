import { setError } from 'actions/common';
import {
  setPhoneNumberAction,
  sendingOTPAction,
  nodeSendingOTPApi,
  nodeVerifyingOTPApi,
  nodeExistingUserApi,
  existingUserAction,
  nodeCreatingUserApi,
  creatingStoreInfoAction,
  creatingUserAction,
  verifyingOTPAction,
  nodeCreatingStoreApi,
} from 'actions/authenticationActions';

export const activationProps = {
  mapStateToProps: ({ authentication }) => ({
    ...authentication,
  }),
  mapDispatchToProps: dispatch => ({
    dispatchError: message => dispatch(setError(message)),
    dispatchSetPhoneNumber: number => dispatch(setPhoneNumberAction(number)),
    dispatchSendOTP: (countryCode, phoneNumber) =>
      dispatch(nodeSendingOTPApi(countryCode, phoneNumber)),
    dispatchExistingUser: phone => dispatch(nodeExistingUserApi(phone)),
    dispatchExistingUserAction: data => dispatch(existingUserAction(data)),
    dispatchCreatingUser: phone => dispatch(nodeCreatingUserApi(phone)),
    dispatchCreatingStoreInfo: data => dispatch(creatingStoreInfoAction(data)),
    dispatchCreatingUserAction: value => dispatch(creatingUserAction(value)),
  }),
};

export const verifyCodeProps = {
  mapStateToProps: ({ authentication }) => ({
    ...authentication,
  }),
  mapDispatchToProps: dispatch => ({
    clearOTPStatus: () => {
      dispatch(sendingOTPAction(false));
      dispatch(verifyingOTPAction(false));
    },
    dispatchVerifyOTPCode: (countryCode, phoneNumber, otpCode) =>
      dispatch(nodeVerifyingOTPApi(countryCode, phoneNumber, otpCode)),
    dispatchCreatingStore: (data, token) =>
      dispatch(nodeCreatingStoreApi(data, token)),
  }),
};

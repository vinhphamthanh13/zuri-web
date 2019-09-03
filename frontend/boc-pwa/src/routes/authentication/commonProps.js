import { setError } from 'actions/common';
import {
  nodeUsersApi,
  setPhoneNumberAction,
  getVerificationCodeAction,
  nodeVerificationCodeApi,
  nodeExistingUserApi,
  existingUserAction,
  nodeCreatingUserApi,
  creatingUserAction,
} from 'actions/authenticationActions';

export const activationProps = {
  mapStateToProps: ({ authentication }) => ({
    phoneNumber: authentication.phoneNumber,
    getVerificationCodeStatus: authentication.getVerificationCodeStatus,
    existingUser: authentication.existingUser,
    creatingUser: authentication.creatingUser,
  }),
  mapDispatchToProps: dispatch => ({
    dispatchError: message => dispatch(setError(message)),
    dispatchUsers: () => dispatch(nodeUsersApi()),
    dispatchSetPhoneNumber: number => dispatch(setPhoneNumberAction(number)),
    dispatchVerificationCode: (countryCode, phoneNumber) =>
      dispatch(nodeVerificationCodeApi(countryCode, phoneNumber)),
    dispatchExistingUser: phone => dispatch(nodeExistingUserApi(phone)),
    dispatchExistingUserAction: data => dispatch(existingUserAction(data)),
    dispatchCreatingUser: phone => dispatch(nodeCreatingUserApi(phone)),
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

export const creatingStoreProps = {
  mapDispatchToProps: dispatch => ({
    dispatchExistingUserAction: data => dispatch(existingUserAction(data)),
    dispatchCreatingUserAction: value => dispatch(creatingUserAction(value)),
  }),
};

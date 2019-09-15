import { setError } from 'actions/commonActions';
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
  verifyingOTPStatusAction,
  nodeCreatingStoreApi,
  creatingStoreStatusAction,
  creatingStoreProgressAction,
  nodeUserByIdApi,
} from 'actions/authenticationActions';
import { selectedShopIdAction } from 'actions/shopsActions';

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
    dispatchCleanUpAuthentication: () => {
      dispatch(creatingStoreStatusAction(null));
      dispatch(creatingStoreProgressAction(null));
      dispatch(verifyingOTPStatusAction(null));
    },
  }),
};

export const verifyCodeProps = {
  mapStateToProps: ({ authentication }) => ({
    ...authentication,
  }),
  mapDispatchToProps: dispatch => ({
    clearOTPStatus: () => {
      dispatch(sendingOTPAction(false));
      dispatch(verifyingOTPAction({ verifyingOTPStatus: null }));
    },
    dispatchVerifyOTPCode: (countryCode, phoneNumber, otpCode) =>
      dispatch(nodeVerifyingOTPApi(countryCode, phoneNumber, otpCode)),
    dispatchCreatingStore: (data, token) =>
      dispatch(nodeCreatingStoreApi(data, token)),
    dispatchReSendOTP: (countryCode, phoneNumber) =>
      dispatch(nodeSendingOTPApi(countryCode, phoneNumber)),
    dispatchCleanUpAuthentication: () => {
      dispatch(creatingStoreStatusAction(null));
      dispatch(creatingStoreProgressAction(null));
      dispatch(verifyingOTPStatusAction(null));
    },
  }),
};

export const shopsProps = {
  mapStateToProps: ({
    shops,
    authentication: { userDetail, accessToken },
  }) => ({
    accessToken,
    userDetail,
    ...shops,
  }),
  mapDispatchToProps: dispatch => ({
    dispatchSelectedShopId: id => dispatch(selectedShopIdAction(id)),
    dispatchGettingUserById: id => dispatch(nodeUserByIdApi(id)),
  }),
};

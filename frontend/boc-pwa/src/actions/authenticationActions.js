/*
 * Naming Rule
 *  1. Request to node server action: nodeActionName
 *  2. Action consumes request: nodeActionNameApi
 *  3. Redux action: actionNameAction
 */

import axios from 'axios';
import { get } from 'lodash';
import { NODE_SERVER_URL, PROXY_AUTH } from 'actions/constants';
import { DATA } from 'constants/common';
import { handleRequest } from 'api/utils';
import { LOADING, setLoading, setError } from 'actions/commonActions';
import { HTTP_STATUS } from 'constants/http';

// Action API to Node Server

const nodeSendingOTP = (countryCode, phoneNumber) =>
  axios.get(
    `${PROXY_AUTH}${NODE_SERVER_URL.SENDING_OTP}/${countryCode}/${phoneNumber}`,
  );
const nodeVerifyingOTP = (countryCode, phoneNumber, otpCode) =>
  axios.get(
    `${PROXY_AUTH}${
      NODE_SERVER_URL.VERIFYING_OTP
    }/${countryCode}/${phoneNumber}/${otpCode}`,
  );
const nodeCreatingUser = data =>
  axios.post(`${PROXY_AUTH}${NODE_SERVER_URL.CREATING_USER}`, data);
const nodeExistingUser = phone =>
  axios.get(`${PROXY_AUTH}${NODE_SERVER_URL.EXISTING_USER}/${phone}`);
const nodeUserById = id =>
  axios.get(`${PROXY_AUTH}${NODE_SERVER_URL.GETTING_USER}/${id}`);
const nodeCreatingStore = (data, token) =>
  axios.post(`${PROXY_AUTH}${NODE_SERVER_URL.CREATING_STORE}`, { data, token });

// Redux constants
export const SET_USERS = 'AUTH.SET_USERS';
export const SET_PHONE_NUMBER = 'AUTH.SET_PHONE_NUMBER';
export const SENDING_OTP = 'AUTH.SENDING_OTP';
export const VERIFYING_OTP = 'AUTH.VERIFYING_OTP';
export const VERIFYING_OTP_STATUS = 'AUTH.VERIFYING_OTP_STATUS';
export const CREATING_USER = 'AUTH.CREATING_USER';
export const EXISTING_USER = 'AUTH.EXISTING_USER';
export const SETTING_USER_DETAIL = 'AUTH.SETTING_USER_DETAIL';
export const CREATING_STORE = 'AUTH.CREATING_STORE';
export const CREATING_STORE_INFO = 'AUTH.CREATING_STORE_INFO';
export const CREATING_STORE_STATUS = 'AUTH.CREATING_STORE_STATUS';
export const CREATING_STORE_PROGRESS = 'AUTH.CREATING_STORE_PROGRESS';
export const GETTING_USER = 'AUTH.GETTING_USER';

// Redux action
export const setPhoneNumberAction = payload => ({
  type: SET_PHONE_NUMBER,
  payload,
});
export const sendingOTPAction = payload => ({
  type: SENDING_OTP,
  payload,
});
export const verifyingOTPAction = payload => ({
  type: VERIFYING_OTP,
  payload,
});
export const verifyingOTPStatusAction = payload => ({
  type: VERIFYING_OTP_STATUS,
  payload,
});
export const creatingUserAction = payload => ({
  type: CREATING_USER,
  payload,
});
export const existingUserAction = payload => ({
  type: EXISTING_USER,
  payload,
});
export const gettingUserByIdAction = payload => ({
  type: GETTING_USER,
  payload,
});
export const creatingStoreInfoAction = payload => ({
  type: CREATING_STORE_INFO,
  payload,
});
export const creatingStoreAction = payload => ({
  type: CREATING_STORE,
  payload,
});
export const creatingStoreStatusAction = payload => ({
  type: CREATING_STORE_STATUS,
  payload,
});
export const creatingStoreProgressAction = payload => ({
  type: CREATING_STORE_PROGRESS,
  payload,
});
export const settingUserDetailAction = payload => ({
  type: SETTING_USER_DETAIL,
  payload,
});

// Consuming actions
export const nodeCreatingUserApi = phone => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const data = { phone };
  const [result, error] = await handleRequest(nodeCreatingUser, [data]);
  const code = get(result, DATA.CODE) || get(error, DATA.CODE);
  const success = get(result, DATA.SUCCESS) || get(error, DATA.SUCCESS);
  if (code === HTTP_STATUS.INTERNAL_ERROR) {
    const message = get(result, DATA.MESSAGE) || get(error, DATA.MESSAGE);
    dispatch(setError(message));
  }
  dispatch(creatingUserAction(success));
  dispatch(setLoading(LOADING.OFF));
};
export const nodeSendingOTPApi = (
  countryCode,
  phoneNumber,
) => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeSendingOTP, [
    countryCode,
    phoneNumber,
  ]);
  if (error) {
    const message = get(error, DATA.MESSAGE);
    dispatch(setError(message));
  } else {
    const sendOTPStatus = get(result, DATA.SUCCESS);
    dispatch(sendingOTPAction(sendOTPStatus));
  }
  dispatch(setLoading(LOADING.OFF));
};
export const nodeVerifyingOTPApi = (
  countryCode,
  phoneNumber,
  otpCode,
) => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeVerifyingOTP, [
    countryCode,
    phoneNumber,
    otpCode,
  ]);
  if (error) {
    const message = get(error, DATA.MESSAGE);
    dispatch(setError(message));
  } else {
    const verifyingOTPStatus = get(result, DATA.SUCCESS);
    const accessToken = get(result, DATA.TOKEN);
    const userDetail = get(result, DATA.OBJECT);
    dispatch(verifyingOTPAction({ verifyingOTPStatus, accessToken }));
    dispatch(settingUserDetailAction(userDetail));
  }
  dispatch(setLoading(LOADING.OFF));
};
export const nodeExistingUserApi = phone => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeExistingUser, [phone]);
  const data = get(result, DATA.ROOT) || get(error, DATA.ROOT);
  const code = get(data, 'code');
  if (code === HTTP_STATUS.INTERNAL_ERROR) {
    const message = get(data, 'message');
    dispatch(setError(message));
  }
  dispatch(existingUserAction(data));
  dispatch(setLoading(LOADING.OFF));
};

export const nodeUserByIdApi = id => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeUserById, [id]);
  if (error) {
    const message = get(error, DATA.MESSAGE);
    dispatch(setError(message));
  } else {
    const userDetail = get(result, DATA.OBJECT);
    dispatch(gettingUserByIdAction(userDetail));
  }
  dispatch(setLoading(LOADING.OFF));
};

export const nodeCreatingStoreApi = (data, token) => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  dispatch(creatingStoreProgressAction(true));
  const [result, error] = await handleRequest(nodeCreatingStore, [data, token]);
  if (error) {
    const message = get(error, DATA.MESSAGE);
    dispatch(setError(message));
  } else {
    const creatingStoreStatus = get(result, DATA.SUCCESS);
    const storeInfo = get(result, DATA.OBJECT);
    dispatch(creatingStoreAction({ creatingStoreStatus, storeInfo }));
  }
  dispatch(setLoading(LOADING.OFF));
};

/* eslint-disable no-return-await */

/*
 * Naming Rule
 *  1. Request to node server action: nodeActionName
 *  2. Action consumes request: nodeActionNameApi
 *  3. Redux action: actionNameAction
 */

import axios from 'axios';
import { get } from 'lodash';
import { NODE_SERVER_URL } from 'actions/api';
import { DATA } from 'constants/common';
import { handleRequest } from 'api/utils';
import { LOADING, setLoading, setError } from 'actions/common';
import { HTTP_STATUS } from 'constants/http';

const authRootUrl = NODE_SERVER_URL.AUTHENTICATION.ROOT;
const authUrl = NODE_SERVER_URL.AUTHENTICATION;
const activatingPhoneUrl = `${authRootUrl}${authUrl.ACTIVATION}`;
// const verificationCodeUrl = `${authRootUrl}${authUrl.VERIFICATION}`;
const creatingUserUrl = `${authRootUrl}${authUrl.CREATING_USER}`;
const existingUserUrl = `${authRootUrl}${authUrl.EXIST_USER}`;

// Action API to Node Server

const nodeUsers = () => axios.get(authUrl);
const nodeVerificationCode = (countryCode, phoneNumber) =>
  axios.get(`${activatingPhoneUrl}/${countryCode}/${phoneNumber}`);
const nodeVerifiedCode = (countryCode, phoneNumber, verifiedCode) =>
  axios.get(
    `${activatingPhoneUrl}/${countryCode}/${phoneNumber}/${verifiedCode}`,
  );
const nodeCreatingUser = data => axios.post(creatingUserUrl, data);
const nodeExistingUser = phone => axios.get(`${existingUserUrl}/${phone}`);

// Redux constants

export const SET_USERS = 'AUTH.SET_USERS';
export const SET_PHONE_NUMBER = 'AUTH.SET_PHONE_NUMBER';
export const GET_VERIFICATION_CODE = 'AUTH.GET_VERIFICATION_CODE';
export const SET_VERIFICATION_CODE = 'AUTH.SET_VERIFICATION_CODE';
export const CREATING_USER = 'AUTH.CREATING_USER';
export const EXISTING_USER = 'AUTH.EXISTING_USER';

// Redux action

const setUsersAction = payload => ({
  type: SET_USERS,
  payload,
});
export const setPhoneNumberAction = payload => ({
  type: SET_PHONE_NUMBER,
  payload,
});
export const getVerificationCodeAction = payload => ({
  type: GET_VERIFICATION_CODE,
  payload,
});
export const setVerificationCodeAction = payload => ({
  type: SET_VERIFICATION_CODE,
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

// Consuming actions

export const nodeUsersApi = () => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeUsers, []);
  if (error) {
    const message = get(error, DATA.MESSAGE);
    dispatch(setError(message));
  } else {
    dispatch(setUsersAction(result));
  }
  dispatch(setLoading(LOADING.OFF));
};

export const nodeVerificationCodeApi = (
  countryCode,
  phoneNumber,
) => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeVerificationCode, [
    countryCode,
    phoneNumber,
  ]);
  if (error) {
    const message = get(error, DATA.MESSAGE);
    dispatch(setError(message));
  } else {
    const getVerificationCodeStatus = get(result, DATA.SUCCESS);
    dispatch(getVerificationCodeAction(getVerificationCodeStatus));
  }
  dispatch(setLoading(LOADING.OFF));
};

export const nodeVerifiedCodeApi = (
  countryCode,
  phoneNumber,
  verifiedCode,
) => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeVerifiedCode, [
    countryCode,
    phoneNumber,
    verifiedCode,
  ]);
  if (error) {
    const message = get(error, DATA.MESSAGE);
    dispatch(setError(message));
  } else {
    const setVerificationCodeStatus = get(result, DATA.SUCCESS);
    dispatch(setVerificationCodeAction(setVerificationCodeStatus));
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

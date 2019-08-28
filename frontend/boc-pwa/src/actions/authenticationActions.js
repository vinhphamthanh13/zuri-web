/* eslint-disable no-return-await */

/*
 * Naming Rule
 *  1. Request to node server action: nodeActionName
 *  2. Action consumes request: nodeActionNameApi
 *  3. Redux action: actionNameAction
 */

import axios from 'axios';
import { get } from 'lodash';
import { NODE_SERVER_URL } from 'constants/api';
import { handleRequest } from 'api/utils';
import { LOADING, setLoading, setError } from 'actions/common';

const authRootUrl = NODE_SERVER_URL.AUTHENTICATION.ROOT;
const authUrl = NODE_SERVER_URL.AUTHENTICATION;
const activatingPhoneUrl = `${authRootUrl}${authUrl.ACTIVATION}`;
const verificationCodeUrl = `${authRootUrl}${authUrl.VERIFICATION}`;
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
    const message = get(error, 'data.message');
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
    const message = get(error, 'data.message');
    dispatch(setError(message));
  } else {
    const getVerificationCodeStatus = get(result, 'data.success');
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
    const message = get(error, 'data.message');
    dispatch(setError(message));
  } else {
    const setVerificationCodeStatus = get(result, 'data.success');
    dispatch(setVerificationCodeAction(setVerificationCodeStatus));
  }
  dispatch(setLoading(LOADING.OFF));
};

export const nodeCreatingUserApi = data => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeCreatingUser, [data]);
  if (error) {
    const message = get(error, 'data.message');
    dispatch(setError(message));
  } else {
    dispatch(creatingUserAction(result));
  }
  dispatch(setLoading(LOADING.OFF));
};

export const nodeExistingUserApi = phone => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeExistingUser, [phone]);
  const data = get(result, 'data') || get(error, 'data');
  dispatch(existingUserAction(data));
  dispatch(setLoading(LOADING.OFF));
};

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

const authenticationUrl = NODE_SERVER_URL.AUTHENTICATION.ROOT;
const activatingPhone = `${authenticationUrl}${
  NODE_SERVER_URL.AUTHENTICATION.ACTIVATION
}`;

// Action API to Node Server

const nodeUsers = () => axios.get(authenticationUrl);
const nodeVerificationCode = (countryCode, phoneNumber) =>
  axios.get(`${activatingPhone}/${countryCode}/${phoneNumber}`);

// Redux constants

export const SET_USERS = 'AUTH.SET_USERS';
export const SET_PHONE_NUMBER = 'AUTH.SET_PHONE_NUMBER';
export const GET_VERIFICATION_CODE = 'AUTH.GET_VERIFICATION_CODE';

// Redux action

const setUsersAction = payload => ({
  type: SET_USERS,
  payload,
});

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

export const setPhoneNumberAction = payload => ({
  type: SET_PHONE_NUMBER,
  payload,
});

export const verificationCodeAction = payload => ({
  type: GET_VERIFICATION_CODE,
  payload,
});

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
    dispatch(verificationCodeAction(getVerificationCodeStatus));
  }
  dispatch(setLoading(LOADING.OFF));
};

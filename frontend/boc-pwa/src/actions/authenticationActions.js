/* eslint-disable no-return-await */

import axios from 'axios';
import { get } from 'lodash';
import { NODE_SERVER_URL } from 'constants/api';
import { handleRequest } from 'api/utils';
import { LOADING, setLoading, setError } from 'actions/common';
// import { SET_PHONE_NUMBER, SET_USER_ID } from 'constants/authentication';

// Action API to Node Server

const authURL = NODE_SERVER_URL.AUTHENTICATION.ROOT;
const activatePhone = `${authURL}${NODE_SERVER_URL.AUTHENTICATION.ACTIVATION}`;
const getUsers = () => axios.get(authURL);

const getVerifiedCode = (countryCode, phoneNumber) =>
  axios.get(`${activatePhone}/${countryCode}/${phoneNumber}`);

// Redux constants

export const SET_USERS = 'AUTH.SET_USERS';
export const SET_PHONE_NUMBER = 'AUTH.SET_PHONE_NUMBER';
export const GET_VERIFIED_CODE = 'AUTH.SET_VERIFIED_CODE';

// Redux action

const setUsersAction = payload => ({
  type: SET_USERS,
  payload,
});

export const setUsers = () => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(getUsers, []);
  if (error) {
    const message = get(error, 'data.message');
    dispatch(setError(message));
  } else {
    dispatch(setUsersAction(result));
  }
  dispatch(setLoading(LOADING.OFF));
};

export const setShopPhoneAction = payload => ({
  type: SET_PHONE_NUMBER,
  payload,
});

const setVerifiedCodeAction = payload => ({
  type: GET_VERIFIED_CODE,
  payload,
});

export const setVerifiedCode = (countryCode, phoneNumber) => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(getVerifiedCode, [
    countryCode,
    phoneNumber,
  ]);
  if (error) {
    const message = get(error, 'data.message');
    dispatch(setError(message));
  } else {
    const getVerifiedCodeStatus = get(result, 'data.success');
    dispatch(setVerifiedCodeAction(getVerifiedCodeStatus));
  }
  dispatch(setLoading(LOADING.OFF));
};

/* eslint-disable no-return-await */

import axios from 'axios';
import { get } from 'lodash';
import { NODE_SERVER_URL } from 'constants/api';
import { handleRequest } from 'api/utils';
import { setLoading, setError, LOADING } from 'actions/common';
// import { SET_PHONE_NUMBER, SET_USER_ID } from 'constants/authentication';

// Action API to Node Server

const getUsers = () => axios.get(NODE_SERVER_URL.USERS);

// const generateCode = (countryCode, phoneNumber) =>
//   axios.post(`${NODE_SERVER_URL.VERIFY_CODE}/${countryCode}/${phoneNumber}`);
// });

// Redux constants

export const SET_USERS = 'AUTH.SET_USERS';

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

// export const setPhoneNumberAction = payload => ({
//   type: SET_PHONE_NUMBER,
//   payload,
// });
//
// const setUserIdAction = payload => ({
//   type: SET_USER_ID,
//   payload,
// });
//
// const setVerificationCodeStatus = payload => ({
//   type: SET_VERIFICATION_CODE_STATUS,
//   payload,

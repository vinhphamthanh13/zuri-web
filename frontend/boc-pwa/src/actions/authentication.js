import axios from 'axios';
import { get } from 'lodash';
import { NODE_URL } from 'constants/api';
import { handleRequest } from 'api/utils';
import { setLoading, setError, LOADING } from 'actions/common';
import {
  SET_PHONE_NUMBER,
  SET_USER_ID,
  SET_VERIFICATION_CODE_STATUS,
} from 'constants/authentication';

// Action API to Node Server
const loginUrl = number => axios.get(`${NODE_URL.LOGIN}/${number}`);
const generateCode = (countryCode, phoneNumber) =>
  axios.post(`${NODE_URL.VERIFY_CODE}/${countryCode}/${phoneNumber}`);

// Action Redux
export const setPhoneNumberAction = payload => ({
  type: SET_PHONE_NUMBER,
  payload,
});

const setUserIdAction = payload => ({
  type: SET_USER_ID,
  payload,
});

const setVerificationCodeStatus = payload => ({
  type: SET_VERIFICATION_CODE_STATUS,
  payload,
});

export const loginPhoneAction = number => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(loginUrl, [number]);
  const success = get(result, 'data.success');
  const message = get(result, 'data.message');
  if (!success || error) {
    dispatch(setError(message || error));
  } else {
    dispatch(setUserIdAction(result));
  }
  dispatch(setLoading(LOADING.OFF));
};

export const generateVerificationCode = (
  countryCode,
  phoneNumber,
) => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(generateCode, [
    countryCode,
    phoneNumber,
  ]);
  const success = get(result, 'data.success');
  const message = get(result, 'data.message');
  if (!success || error) {
    dispatch(setError(message || error));
  } else {
    dispatch(setVerificationCodeStatus(result));
  }
  dispatch(setLoading(LOADING.OFF));
};

/*
 * Naming Rule
 *  1. Request to server action: serverActionName
 *  2. Action consumes request: serverActionNameApi
 */

import express from 'express';
import axios from 'axios/index';
import { SERVER_SITE_URL } from 'api/constants';
import {
  NODE_SERVER_URL,
  SEND_OTP_PARAMS,
  VERIFY_OTP_PARAMS,
} from 'actions/constants';
import {
  handleNodeServerResponse,
  handleNodeServerError,
  handleRequest,
  createHeaders,
} from 'api/utils';

const router = express.Router();

/* Request server */

const serverCreatingUser = body =>
  axios.post(SERVER_SITE_URL.CREATING_USER, body);
const serverSendOTP = data => axios.post(SERVER_SITE_URL.SENDING_OTP, data);
const serverVerifyOTP = data => axios.post(SERVER_SITE_URL.VERIFYING_OTP, data);
const serverExistingUser = phone =>
  axios.get(`${SERVER_SITE_URL.CHECKING_USER}/${phone}`);
const serverCreatingStore = (data, token) =>
  axios.post(SERVER_SITE_URL.CREATING_STORE, data, createHeaders(token));
/* Consuming actions */

const serverSendOTPApi = async data => {
  const [result, error] = await handleRequest(serverSendOTP, [data]);
  if (error) return error;
  return result;
};
const serverVerifyOTPApi = async data => {
  const [result, error] = await handleRequest(serverVerifyOTP, [data]);
  if (error) return error;
  return result;
};
const serverExistingUserApi = async phone => {
  const [result, error] = await handleRequest(serverExistingUser, [phone]);
  if (error) return error;
  return result;
};
const serverCreatingUserApi = async phone => {
  const [result, error] = await handleRequest(serverCreatingUser, [phone]);
  if (error) return error;
  return result;
};

const serverCreatingStoreApi = async (data, token) => {
  const [result, error] = await handleRequest(serverCreatingStore, [
    data,
    token,
  ]);
  if (error) return error;
  return result;
};

/* ***************
// PROXY ROUTING
***************** */

router.post(NODE_SERVER_URL.CREATING_USER, async (request, response) => {
  const { body } = request;
  try {
    const result = await serverCreatingUserApi(body);
    handleNodeServerResponse(response, result);
  } catch (error) {
    handleNodeServerError(response, error);
  }
});

router.get(
  `${NODE_SERVER_URL.SENDING_OTP}${SEND_OTP_PARAMS}`,
  async (request, response) => {
    const {
      params: { countryCode, phoneNumber },
    } = request;
    const body = {
      countryCode,
      phoneNumber,
    };
    try {
      const result = await serverSendOTPApi(body);
      handleNodeServerResponse(response, result);
    } catch (error) {
      handleNodeServerError(response, error);
    }
  },
);

router.get(
  `${NODE_SERVER_URL.VERIFYING_OTP}${VERIFY_OTP_PARAMS}`,
  async (request, response) => {
    const {
      params: { countryCode, phoneNumber, otpCode },
    } = request;
    const body = {
      countryCode,
      phoneNumber,
      optCode: otpCode,
    };

    try {
      const result = await serverVerifyOTPApi(body);
      handleNodeServerResponse(response, result);
    } catch (error) {
      handleNodeServerError(response, error);
    }
  },
);

router.get(
  `${NODE_SERVER_URL.EXISTING_USER}/:phone`,
  async (request, response) => {
    const {
      params: { phone },
    } = request;
    try {
      const result = await serverExistingUserApi(phone);
      handleNodeServerResponse(response, result);
    } catch (error) {
      handleNodeServerError(response, error);
    }
  },
);

router.post(NODE_SERVER_URL.CREATING_USER, async (request, response) => {
  const {
    body: { data, token },
  } = request;
  // TODO: transform data
  try {
    const result = await serverCreatingStoreApi(data, token);
    handleNodeServerResponse(response, result);
  } catch (error) {
    handleNodeServerError(response, error);
  }
});

export default router;

/*
 * Naming Rule
 *  1. Request to server action: serverActionName
 *  2. Action consumes request: serverActionNameApi
 */

import express from 'express';
import axios from 'axios/index';
import { SERVER_SITE_URL } from 'api/constants';
import { NODE_SERVER_URL } from 'actions/constants';
import {
  handleNodeServerResponse,
  handleNodeServerError,
  handleRequest,
} from 'api/utils';

const router = express.Router();

// Request server

const serverCreatingUser = body =>
  axios.post(SERVER_SITE_URL.CREATING_USER, body);
const serverSendOTP = data => axios.post(SERVER_SITE_URL.SENDING_OTP, data);
const serverVerifyOTP = data => axios.post(SERVER_SITE_URL.VERIFYING_OTP, data);
const serverUsers = () => axios.get(SERVER_SITE_URL.USERS);
const serverExistingUser = phone =>
  axios.get(`${SERVER_SITE_URL.CHECKING_USER}/${phone}`);

// Consuming actions
const serverUsersApi = async () => {
  const [result, error] = await handleRequest(serverUsers, []);
  if (error) return error;
  return result;
};
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

// Routing

/*
 * Create new user
 */

router.post(NODE_SERVER_URL.AUTH.CREATING_USER, async (request, response) => {
  const { body } = request;
  try {
    const result = await serverCreatingUserApi(body);
    handleNodeServerResponse(response, result);
  } catch (error) {
    handleNodeServerError(response, error);
  }
});

/*
 * Get/verify Send OTP
 */

router.get(`${NODE_SERVER_URL.AUTH.SENDING_OTP}`, async (request, response) => {
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
});

router.post(`${NODE_SERVER_URL.AUTH.VERIFY_OTP}`, async (request, response) => {
  const { body } = request;
  try {
    const result = await serverVerifyOTPApi(body);
    handleNodeServerResponse(response, result);
  } catch (error) {
    handleNodeServerError(response, error);
  }
});

/*
 * Get all users
 */

router.get('/', async (request, response) => {
  try {
    const [result, error] = await handleRequest(serverUsersApi, []);
    if (error) {
      handleNodeServerError(response, error);
    } else {
      handleNodeServerResponse(response, result);
    }
  } catch (error) {
    handleNodeServerError(response, error);
  }
});

router.get(
  `${NODE_SERVER_URL.AUTH.EXISTING_USER}/:phone`,
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

export default router;

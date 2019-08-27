/*
 * Naming Rule
 *  1. Request to server action: serverActionName
 *  2. Action consumes request: serverActionNameApi
 */

import express from 'express';
import axios from 'axios/index';
import { get } from 'lodash';
import { SERVER_URL } from 'api/constant';
import { NODE_SERVER_URL } from 'constants/api';
import {
  handleNodeServerResponse,
  handleNodeServerError,
  handleRequest,
} from 'api/utils';

const router = express.Router();

// API for request handler

const serverUsers = () => axios.get(SERVER_URL.USERS);
const serverVerificationCode = (countryCode, phoneNumber) =>
  axios.get(`${SERVER_URL.USERS}/${countryCode}/${phoneNumber}`);
const serverCreatingUser = body => axios.post(SERVER_URL.USERS, body);

const serverUsersApi = async () => {
  const [result, error] = await handleRequest(serverUsers, []);
  if (error) return error;
  return result;
};

const serverVerificationCodeApi = async (countryCode, phoneNumber) => {
  const [result, error] = await handleRequest(serverVerificationCode, [
    countryCode,
    phoneNumber,
  ]);
  if (error) return error;
  return result;
};

const serverCreatingUserApi = async (countryCode, phone, verificationCode) => {
  const data = {
    countryCode,
    phone,
    verificationCode,
  };
  const [result, error] = await handleRequest(serverCreatingUser, [data]);
  if (error) return error;
  return result;
};

// Routing

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

/*
 * Get Verification Code
 */

router.get(
  `${NODE_SERVER_URL.AUTHENTICATION.ACTIVATION}/:countryCode/:phoneNumber`,
  async (request, response) => {
    const { params } = request;
    const countryCode = get(params, 'countryCode');
    const phoneNumber = get(params, 'phoneNumber');
    try {
      const result = await serverVerificationCodeApi(countryCode, phoneNumber);
      handleNodeServerResponse(response, result);
    } catch (error) {
      handleNodeServerError(response, error);
    }
  },
);

/*
 * Create new user
 */

router.post(
  NODE_SERVER_URL.AUTHENTICATION.CREATING_USER,
  async (request, response) => {
    const { body } = request;
    const countryCode = get(body, 'countryCode');
    const phone = get(body, 'phoneNumber');
    const verificationCode = get(body, 'verificationCode');
    try {
      const result = await serverCreatingUserApi(
        countryCode,
        phone,
        verificationCode,
      );
      handleNodeServerResponse(response, result);
    } catch (error) {
      handleNodeServerError(response, error);
    }
  },
);
export default router;

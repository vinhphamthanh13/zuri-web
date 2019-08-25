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

const getUsers = () => axios.get(SERVER_URL.USERS);
const getVerifiedCode = (countryCode, phoneNumber) =>
  axios.get(`${SERVER_URL.GET_VERIFIED_CODE}/${countryCode}/${phoneNumber}`);

const allUsers = async () => {
  const [result, error] = await handleRequest(getUsers, []);
  if (error) return error;
  return result;
};

const activation = async (countryCode, phoneNumber) => {
  const [result, error] = await handleRequest(getVerifiedCode, [
    countryCode,
    phoneNumber,
  ]);
  if (error) return error;
  return result;
};

// Routing

router.get('/', async (request, response) => {
  try {
    const [result, error] = await handleRequest(allUsers, []);
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
  `${NODE_SERVER_URL.AUTHENTICATION.ACTIVATION}/:countryCode/:phoneNumber`,
  async (request, response) => {
    const { params } = request;
    const countryCode = get(params, 'countryCode');
    const phoneNumber = get(params, 'phoneNumber');
    try {
      const result = await activation(countryCode, phoneNumber);
      handleNodeServerResponse(response, result);
    } catch (error) {
      handleNodeServerError(response, error);
    }
  },
);

export default router;

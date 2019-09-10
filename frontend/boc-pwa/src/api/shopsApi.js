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
  createHeaders,
} from 'api/utils';

const router = express.Router();

/* Request server */

const serverGettingStore = (id, token) =>
  axios.get(`${SERVER_SITE_URL.STORE}/${id}`, createHeaders(token));

/* Consuming actions */
const serverGettingStoreApi = async (id, token) => {
  const [result, error] = await handleRequest(serverGettingStore, [id, token]);
  if (error) return error;
  return result;
};

/* ***************
// PROXY ROUTING
***************** */
router.get(
  `${NODE_SERVER_URL.GETTING_STORE}/:shopId/:token`,
  async (request, response) => {
    const {
      params: { shopId, token },
    } = request;
    try {
      const result = await serverGettingStoreApi(shopId, token);
      handleNodeServerResponse(response, result);
    } catch (error) {
      handleNodeServerError(response, error);
    }
  },
);

export default router;

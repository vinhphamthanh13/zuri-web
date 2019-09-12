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
const serverUpdatingStore = (data, token) =>
  axios.put(SERVER_SITE_URL.STORE, data, createHeaders(token));

/* Consuming actions */
const serverGettingStoreApi = async (id, token) => {
  const [result, error] = await handleRequest(serverGettingStore, [id, token]);
  if (error) return error;
  return result;
};
const serverUpdatingStoreApi = async (data, token) => {
  const body = {
    address: data.shopAddress,
    danhMucMatHangType: data.categoryType,
    id: data.id,
    managerEmail: data.userEmail,
    moHinhKinhDoanhType: data.businessType,
  };
  const [result, error] = await handleRequest(serverUpdatingStore, [
    body,
    token,
  ]);
  if (error) return error;
  return result;
};

/* ***************
// PROXY ROUTING
***************** */
router.get(
  `${NODE_SERVER_URL.GETTING_STORE}/:shopId/:token`,
  async (req, res) => {
    const {
      params: { shopId, token },
    } = req;
    try {
      const result = await serverGettingStoreApi(shopId, token);
      handleNodeServerResponse(res, result);
    } catch (error) {
      handleNodeServerError(res, error);
    }
  },
);

router.get(
  `${NODE_SERVER_URL.UPDATING_STORE}/:updatingInfo/:token`,
  async (req, res) => {
    const {
      params: { updatingInfo, token },
    } = req;
    const body = JSON.parse(updatingInfo);
    try {
      const result = await serverUpdatingStoreApi(body, token);
      handleNodeServerResponse(res, result);
    } catch (error) {
      handleNodeServerError(res, error);
    }
  },
);

export default router;

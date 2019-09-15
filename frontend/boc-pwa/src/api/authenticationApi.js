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
const serverUserById = id => axios.get(`${SERVER_SITE_URL.USERS}/${id}`);
const serverCreatingStore = (data, token) =>
  axios.post(SERVER_SITE_URL.STORE, data, createHeaders(token));
const serverGettingStore = (id, token) =>
  axios.get(`${SERVER_SITE_URL.STORE}/${id}`, createHeaders(token));

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
const serverUserByIdApi = async id => {
  const [result, error] = await serverUserById(id);
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
const serverGettingStoreApi = async (id, token) => {
  const [result, error] = await handleRequest(serverGettingStore, [id, token]);
  if (error) return error;
  return result;
};

/* ***************
// PROXY ROUTING
***************** */

router.post(NODE_SERVER_URL.CREATING_USER, async (req, res) => {
  const { body } = req;
  try {
    const result = await serverCreatingUserApi(body);
    handleNodeServerResponse(res, result);
  } catch (error) {
    handleNodeServerError(res, error);
  }
});
router.get(
  `${NODE_SERVER_URL.SENDING_OTP}${SEND_OTP_PARAMS}`,
  async (req, res) => {
    const {
      params: { countryCode, phoneNumber },
    } = req;
    const body = {
      countryCode,
      phoneNumber,
    };
    try {
      const result = await serverSendOTPApi(body);
      handleNodeServerResponse(res, result);
    } catch (error) {
      handleNodeServerError(res, error);
    }
  },
);
router.get(
  `${NODE_SERVER_URL.VERIFYING_OTP}${VERIFY_OTP_PARAMS}`,
  async (req, res) => {
    const {
      params: { countryCode, phoneNumber, otpCode },
    } = req;
    const body = {
      countryCode,
      phoneNumber,
      otpCode,
    };

    try {
      const result = await serverVerifyOTPApi(body);
      handleNodeServerResponse(res, result);
    } catch (error) {
      handleNodeServerError(res, error);
    }
  },
);
router.get(`${NODE_SERVER_URL.EXISTING_USER}/:phone`, async (req, res) => {
  const {
    params: { phone },
  } = req;
  try {
    const result = await serverExistingUserApi(phone);
    handleNodeServerResponse(res, result);
  } catch (error) {
    handleNodeServerError(res, error);
  }
});
router.get(`${NODE_SERVER_URL.GETTING_USER}/:id`, async (req, res) => {
  const {
    params: { id },
  } = req;
  try {
    const result = await serverUserByIdApi(id);
    handleNodeServerResponse(res, result);
  } catch (error) {
    handleNodeServerError(res, error);
  }
});
router.post(NODE_SERVER_URL.CREATING_STORE, async (req, res) => {
  const {
    body: { data, token },
  } = req;
  const creatingStoreData = {
    address: data.shopAddress,
    cuaHangName: data.shopName,
    danhMucMatHangType: data.categoryType,
    managerEmail: data.userEmail,
    managerName: data.userName,
    managerPhone: data.phoneNumber,
    moHinhKinhDoanhType: data.businessType,
    phone: data.phoneNumber,
  };
  try {
    const result = await serverCreatingStoreApi(creatingStoreData, token);
    handleNodeServerResponse(res, result);
  } catch (error) {
    handleNodeServerError(res, error);
  }
});
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

export default router;

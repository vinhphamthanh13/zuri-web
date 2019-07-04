import fetch from 'node-fetch';
import { CONTENT_TYPE } from 'constants/http';
import storage from 'node-persist';
import { get } from 'lodash';
import { ACL_API } from 'api/constant';
import config from '../../config';
import { resultHandle, errorHandle, handleRequest, postApi } from '../utils';

const VERIFY_OTP_API = '/financing/v1/verificationCheck';
const TOKEN_API = `${ACL_API}/token`;

const verifyOTP = async (verificationID, otp) => {
  const { url } = config.auth.oapi;
  const token = await storage.getItem('clz_token');

  const result = await fetch(
    `${url}${VERIFY_OTP_API}?verificationID=${verificationID}&codeID=${otp}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': CONTENT_TYPE.JSON,
        Authorization: `Bearer ${token}`,
      },
    },
  );

  const json = await result.json();

  return {
    status: result.status,
    data: json,
  };
};

export default async (req, res) => {
  const {
    body: { phoneNumber, verificationID, otp },
    sessionID,
  } = req;

  try {
    const result = await verifyOTP(verificationID, otp);
    const [data, error] = await handleRequest(postApi, [
      TOKEN_API,
      {
        phoneNumber,
        verificationID,
        otp,
        sessionId: sessionID,
      },
    ]);
    if (error) {
      console.error('Get tracking toke Error', error);
    } else {
      result.data.token = get(data, 'data');
    }
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};

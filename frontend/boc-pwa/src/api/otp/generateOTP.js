import fetch from 'node-fetch';
import storage from 'node-persist';

import { CONTENT_TYPE } from 'constants/http';
import config from '../../config';
import { resultHandle, errorHandle } from '../utils';

const GENERATE_OTP_API = '/financing/v1/verification';

const generateOTP = async phoneNumber => {
  const { url } = config.auth.oapi;
  const token = await storage.getItem('clz_token');

  const result = await fetch(`${url}${GENERATE_OTP_API}`, {
    method: 'POST',
    body: JSON.stringify({
      phoneNumber,
      template: '1BOD template',
    }),
    headers: {
      'Content-Type': CONTENT_TYPE.JSON,
      Authorization: `Bearer ${token}`,
    },
  });

  const json = await result.json();

  return {
    status: result.status,
    data: json,
  };
};

export default async (req, res) => {
  const {
    body: { phoneNumber },
  } = req;

  try {
    const result = await generateOTP(phoneNumber);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};

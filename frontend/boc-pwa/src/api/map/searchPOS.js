import fetch from 'node-fetch';

import { CONTENT_TYPE } from 'constants/http';
import config from '../../config';
import { resultHandle, errorHandle } from '../utils';

const searchPOS = async (lat, long) => {
  const { searchPOSUrl } = config.api;
  const result = await fetch(`${searchPOSUrl}?lat=${lat}&lng=${long}`, {
    method: 'GET',
    headers: {
      'Content-Type': CONTENT_TYPE.JSON,
    },
  });

  const json = await result.json();

  return {
    status: result.status,
    data: json.data,
  };
};

export default async (req, res) => {
  const {
    body: { lat, long },
  } = req;

  try {
    const result = await searchPOS(lat, long);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};

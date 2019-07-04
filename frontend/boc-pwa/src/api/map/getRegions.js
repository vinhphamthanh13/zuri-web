import fetch from 'node-fetch';

import { CONTENT_TYPE } from 'constants/http';
import config from '../../config';
import { resultHandle, errorHandle } from '../utils';

const getRegions = async () => {
  const { url, apikey } = config.masterData;
  const result = await fetch(`${url}/public/v1/regions`, {
    method: 'GET',
    headers: {
      apikey,
      'Content-Type': CONTENT_TYPE.JSON,
    },
  });

  const json = await result.json();

  return {
    status: result.status,
    data: json.data,
  };
};

export default async (_, res) => {
  try {
    const result = await getRegions();
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};

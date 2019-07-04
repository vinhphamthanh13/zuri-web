import fetch from 'node-fetch';

import { CONTENT_TYPE } from 'constants/http';
import config from '../../config';
import { resultHandle, errorHandle } from '../utils';

const getDistricts = async regionCode => {
  const { url, apikey } = config.masterData;
  const result = await fetch(
    `${url}/public/v1/regions/${regionCode}/districts`,
    {
      method: 'GET',
      headers: {
        apikey,
        'Content-Type': CONTENT_TYPE.JSON,
      },
    },
  );

  const json = await result.json();

  return {
    status: result.status,
    data: json.data,
  };
};

export default async (req, res) => {
  const {
    body: { regionCode },
  } = req;

  try {
    const result = await getDistricts(regionCode);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};

import fetch from 'node-fetch';
import { ACL_API } from '../constant';
import { resultHandle, errorHandle, handleRequest } from '../utils';

const generateShortId = code => fetch(`${ACL_API}/generateShortURL/${code}`);

/**
 * Generate Short id from SA Code
 * Find SA Code first , if not exist, insert new record
 * If Exist, return the existing short id
 *
 * @param  {String} saCode Sa Code
 * @return {Object} Short id
 */

const generateShortURL = async saCode => {
  const [response, error] = await handleRequest(generateShortId, [saCode]);
  if (error) {
    console.error('Generating shortURL', error);
  }
  const { data } = response;

  return {
    ...response,
    data: {
      shortId: data,
    },
  };
};

export default async (req, res) => {
  const { saCode } = req.params;

  try {
    const data = await generateShortURL(saCode);
    resultHandle(res, data);
  } catch (err) {
    errorHandle(res, err);
  }
};

import fetch from 'node-fetch';
import { ACL_API } from 'api/constant';
import { get } from 'lodash';
import { HTTP_STATUS } from 'constants/http';
import { resultHandle, errorHandle, handleRequest } from '../utils';

const verifyId = id => fetch(`${ACL_API}/verifyShortId/${id}`);

const verifyShortId = async shortId => {
  const [result, error] = await handleRequest(verifyId, [shortId]);
  if (error) {
    const { status, message, detailMessage } = error;
    return {
      status,
      data: {
        errors: [{ code: message || detailMessage }],
      },
    };
  }

  return {
    status: HTTP_STATUS.OK,
    data: {
      saCode: get(result, 'data.saCode'),
      partnerCode: get(result, 'data.partnerCode'),
    },
  };
};

export default async (req, res) => {
  const { shortId } = req.params;

  try {
    const date = await verifyShortId(shortId);

    resultHandle(res, date);
  } catch (err) {
    errorHandle(res, err);
  }
};

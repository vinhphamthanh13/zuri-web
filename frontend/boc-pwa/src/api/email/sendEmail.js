import fetch from 'node-fetch';
import { ACL_API } from 'api/constant';
import { CONTENT_TYPE } from 'constants/http';

import { resultHandle, errorHandle } from '../utils';

const trackingPOSMap = async posInfo => {
  try {
    await fetch(`${ACL_API}/appointment`, {
      method: 'POST',
      headers: {
        'Content-Type': CONTENT_TYPE.JSON,
      },
      body: JSON.stringify(posInfo),
    });
  } catch (err) {
    console.error(err);
  }
};

/**
 * Call send mail with JAVA API
 * @param {Number} contractId
 * @param {Object} contractInfo
 */
const sendMailToJavaAPI = async (
  contractId,
  { address, appointmentTime, shopCode, shopName },
) => {
  const response = await fetch(`${ACL_API}/send-mail/${contractId}`, {
    method: 'POST',
    headers: {
      'Content-Type': CONTENT_TYPE.JSON,
    },
    body: JSON.stringify({
      address,
      appointmentTime,
      shopCode,
      shopName,
    }),
  });

  const result = await response.json();
  if (result.code !== 0 || response.status !== 200) {
    return Promise.reject(
      new Error(`Error occurs with ${result.message || result.detailMessage}`),
    );
  }

  return response;
};

const sendEmail = async (
  contractId,
  { shopCode, shopName, appointmentTime, address },
) => {
  const result = await sendMailToJavaAPI(contractId, {
    address,
    appointmentTime,
    shopCode,
    shopName,
  });

  // Tracking POS Map
  await trackingPOSMap({
    shopName,
    shopCode,
    contractId,
    address,
    appointmentTime,
  });

  return {
    status: result.status,
    data: { success: result.ok },
  };
};

export default async (req, res) => {
  const {
    body: { contractId, appointmentInfo },
  } = req;

  try {
    const result = await sendEmail(contractId, appointmentInfo);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};

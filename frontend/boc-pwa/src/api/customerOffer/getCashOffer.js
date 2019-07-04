import fetch from 'node-fetch';
import { assign, filter } from 'lodash';
import storage from 'node-persist';

import { CONTENT_TYPE } from 'constants/http';

import config from '../../config';
import { resultHandle, errorHandle } from '../utils';
import { ACL_PARTNER } from '../constant';

const GET_CASH_OFFER_API = '/financing/v1/customerOffers?include=ALL';
const ACL_PRODUCT_CODES = ['CLWLP1', 'CLWLP2', 'CLWLP3'];

export const getCashOffer = async (amount, excludeInsurance, partnerCode) => {
  const { url } = config.auth.oapi;
  const body = {
    loanType: 'CASH_LOAN',
    requestedLoanAmount: {
      amount: amount * 1000000,
      currency: 'VND',
    },
  };
  if (excludeInsurance) {
    assign(body, { hasGoodsInsurance: 'NO', hasLifeInsurance: 'NO' });
  }
  const token = await storage.getItem('clz_token');

  const result = await fetch(`${url}${GET_CASH_OFFER_API}`, {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': CONTENT_TYPE.JSON,
      Authorization: `Bearer ${token}`,
    },
  });

  const json = await result.json();

  // get partnerCodes
  let partnerCodes;
  if (partnerCode) {
    const partnerCoderResponse = await fetch(`${ACL_PARTNER}/${partnerCode}`);
    const { data } = await partnerCoderResponse.json();
    if (data) partnerCodes = data.productCodes;
  }

  const listOffers = filter(json.data, offer => {
    if (partnerCodes)
      return partnerCodes.indexOf(offer.productInformation.code) > -1;

    return ACL_PRODUCT_CODES.indexOf(offer.productInformation.code) > -1;
  });

  return {
    status: result.status,
    data: { data: listOffers },
  };
};

export default async (req, res) => {
  const {
    body: { amount, excludeInsurance, partnerCode },
  } = req;

  try {
    const result = await getCashOffer(amount, excludeInsurance, partnerCode);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};

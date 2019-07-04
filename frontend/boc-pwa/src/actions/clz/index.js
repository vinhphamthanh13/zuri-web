/* eslint-disable import/prefer-default-export */
import {
  CLZ_UPDATE_1BOD_INFO,
  CLZ_UPDATE_FORM_STATUS,
  CLZ_SET_LOADING,
  CLZ_UPDATE_STEP,
  STORE_TRACKING_TOKEN,
  CLZ_CLEAR_1BOD_INFO,
} from 'constants/clz';
import { URL } from 'constants/api';
import { post } from 'utils/http';

export function set1BODInfo({ label, value }) {
  return {
    type: CLZ_UPDATE_1BOD_INFO,
    payload: {
      label,
      value,
    },
  };
}

export function clear1BODInfo() {
  return {
    type: CLZ_CLEAR_1BOD_INFO,
  };
}

export function setFormStatus(value) {
  return {
    type: CLZ_UPDATE_FORM_STATUS,
    payload: value,
  };
}

export function updateLoading(status) {
  return {
    type: CLZ_SET_LOADING,
    payload: status,
  };
}

export function updateStep(step) {
  return {
    type: CLZ_UPDATE_STEP,
    payload: step,
  };
}

export function generateOTPSuccess(payload) {
  const { verificationID } = payload;

  return {
    type: CLZ_UPDATE_1BOD_INFO,
    payload: {
      label: 'verificationID',
      value: verificationID,
    },
  };
}

export function generateOTP(phoneNumber) {
  return post(URL.GENERATE_OTP, { phoneNumber })
    .then(body => body.json())
    .catch(error => console.error('Error:', error));
}

export function storeTrackingToken(payload) {
  return {
    type: STORE_TRACKING_TOKEN,
    payload,
  };
}

export function trackingInfo(info) {
  const { url, token, body } = info;

  return post(`${URL.TRACKING}/${url}`, body, token).catch(error =>
    console.error('Error', error),
  );
}

export function verifyOTP(phoneNumber, verificationID, otp) {
  const body = {
    phoneNumber,
    verificationID,
    otp,
  };

  return post(URL.VERIFY_OTP, body)
    .then(response => response.json())
    .catch(error => console.error('Error:', error));
}

export function getCashOffer(amount, excludeInsurance, partnerCode = '') {
  const body = {
    amount,
    excludeInsurance,
    partnerCode,
  };

  return post(URL.GET_CASH_OFFER, body)
    .then(response => response.json())
    .catch(error => console.error('Error:', error));
}

export function getCashOfferSuccess(bestOffer) {
  return {
    type: CLZ_UPDATE_1BOD_INFO,
    payload: {
      label: 'bestOffer',
      value: bestOffer,
    },
  };
}

export function createApplication({ firstBODInfo, token }) {
  return post(URL.CREATE_APPLICATION, firstBODInfo, token)
    .then(response => response.json())
    .catch(error => console.error('Error:', error));
}

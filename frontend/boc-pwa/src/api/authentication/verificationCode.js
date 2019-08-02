import axios from 'axios';
import { SERVER_URL } from 'api/constant';
import {
  handleNodeServerResponse,
  handleNodeServerError,
  handleRequest,
} from 'api/utils';

const generateVerificationCode = (countryCode, phoneNumber) =>
  axios.post(`${SERVER_URL.VERIFY_CODE}/${countryCode}/${phoneNumber}`);

const verifyingCode = async (countryCode, phoneNumber) => {
  const [result, error] = await handleRequest(generateVerificationCode, [
    countryCode,
    phoneNumber,
  ]);
  if (error) return error;
  return result;
};

export default async (request, response) => {
  const { countryCode, phoneNumber } = request.params;
  try {
    const result = await verifyingCode(countryCode, phoneNumber);
    handleNodeServerResponse(response, result);
  } catch (error) {
    handleNodeServerError(response, error);
  }
};

import axios from 'axios';
import { SERVER_URL } from 'api/constant';
import {
  handleNodeServerResponse,
  handleNodeServerError,
  handleRequest,
} from 'api/utils';

const loginBase = number => axios.get(`${SERVER_URL.LOGIN}/${number}`);

const loginPhone = async number => {
  const [result, error] = await handleRequest(loginBase, [number]);
  if (error) return error;
  return result;
};

export default async (request, response) => {
  const { userId } = request.params;
  try {
    const result = await loginPhone(userId);
    handleNodeServerResponse(response, result);
  } catch (error) {
    handleNodeServerError(response, error);
  }
};

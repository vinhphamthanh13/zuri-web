import axios from 'axios';
import { SERVER_URL } from 'api/constant';
import { loginPhone } from 'actions/authentication';
import { handleServerResponse, handleServerError } from 'api/utils';

export const loginBase = number => axios.get(`${SERVER_URL.LOGIN}/${number}`);

export default async (request, response) => {
  const { userId } = request.params;

  console.log('userId', userId);
  console.log('requries..........>>>>>>>>>>', request);
  try {
    const status = await loginPhone(userId);
    handleServerResponse(response, status);
  } catch (error) {
    handleServerError(response, error);
  }
};

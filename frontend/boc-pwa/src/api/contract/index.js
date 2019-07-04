import config from 'config';
import { CONTENT_TYPE } from 'constants/http';
import { ACL_API } from 'api/constant';
import { resultHandle, errorHandle, handleRequest, fetchApi } from '../utils';

const getContract = async id => {
  let result;
  let error;
  const { url, apiKey } = config.mobileAPI;

  // Get contract from our DB
  [result, error] = await handleRequest(fetchApi, [`${ACL_API}/contract`, id]);

  // Get contract from mobile if don't have in ACL WEB
  if (error) {
    [result, error] = await handleRequest(fetchApi, [
      `${url}/application`,
      id,
      {
        Authorization: `apiKey ${apiKey}`,
        'Content-Type': CONTENT_TYPE.JSON,
      },
    ]);
    if (error) {
      console.error(`Error occurs ${error.message}`);
    }
  }

  return result;
};

export default async (req, res) => {
  const { id } = req.params;

  try {
    const contract = await getContract(id);
    resultHandle(res, contract);
  } catch (err) {
    errorHandle(res, err);
  }
};

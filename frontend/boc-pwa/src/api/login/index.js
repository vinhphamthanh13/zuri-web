import nodeFetch from 'node-fetch';
import https from 'https';
import { startsWith, split } from 'lodash';

import { CONTENT_TYPE } from 'constants/http';
import config from '../../config';

const agent = new https.Agent({
  rejectUnauthorized: false,
});
//
// Get Partner token
// -----------------------------------------------------------------------------
export default async function initToken() {
  try {
    const {
      url,
      clientId,
      clientSecret,
      userName,
      password,
      loginUrl,
    } = config.auth.oapi;
    const grantType = 'authorization_code';

    /*
     * get partner code
     */
    const data = {
      responseType: 'code',
      scope: 'financing',
      clientId,
      credentials: {
        userName,
        password,
      },
    };
    const option = {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': CONTENT_TYPE.JSON,
      },
    };

    // Add agent for https
    if (startsWith(loginUrl, 'https')) {
      option.agent = agent;
    }

    const result = await nodeFetch(loginUrl, option);
    const json = await result.json();
    const code = split(json.redirectUri, 'code=')[1];

    /*
     * get access token
     */
    const tokenReq = await nodeFetch(`${url}/customer/hc-oauth2/token`, {
      method: 'POST',
      body: `grant_type=${grantType}&client_id=${clientId}&client_secret=${clientSecret}&code=${code}`,
      headers: {
        'Content-Type': CONTENT_TYPE.URL_ENCODED,
      },
    });

    const token = await tokenReq.json();

    return token;
  } catch (err) {
    return err;
  }
}

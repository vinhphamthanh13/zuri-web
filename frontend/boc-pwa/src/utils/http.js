import { assign } from 'lodash';

import { CONTENT_TYPE, HTTP_METHOD } from 'constants/http';

export const generateHeaders = (token, contentType = CONTENT_TYPE.JSON) => {
  const header = {
    'Content-Type': contentType,
    Accept: contentType,
    credentials: 'include',
  };

  // Assign the authorization header if has token
  token && assign(header, { Authorization: `Bearer ${token}` });

  return header;
};

export const post = (url, body, token) =>
  fetch(url, {
    method: HTTP_METHOD.POST,
    headers: generateHeaders(token),
    body: JSON.stringify(body),
  });

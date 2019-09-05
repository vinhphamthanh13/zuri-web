import { CONTENT_TYPE, HTTP_METHOD } from 'constants/http';

export const generateHeaders = (token, contentType = CONTENT_TYPE.JSON) => ({
  'Content-Type': contentType,
  Accept: contentType,
  credentials: 'include',
  Authorization: token ? `Bearer ${token}` : null,
});

export const post = (url, body, token) =>
  fetch(url, {
    method: HTTP_METHOD.POST,
    headers: generateHeaders(token),
    body: JSON.stringify(body),
  });

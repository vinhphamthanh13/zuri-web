import { toUpper } from 'lodash';
import { CONTENT_TYPES } from 'constants/common';

const FETCH_METHODS = ['POST', 'PUT'];

export const fetchConfig = (
  { method = 'GET', contentType = CONTENT_TYPES.JSON },
  body,
) => {
  const upCasedMethod = toUpper(method);
  const auth = JSON.parse(localStorage.getItem('internalAuth'));

  const config = {
    method: upCasedMethod,
    headers: {
      Authorization: auth ? `Bearer ${auth.token}` : '',
    },
    credentials: 'same-origin',
  };

  // Do not need add content for form-data, fetch automatically manage it when body is form-data
  if (contentType && toUpper(contentType) !== CONTENT_TYPES.MULTIPART_FORM) {
    config.headers['Content-Type'] = contentType;
  }

  if (FETCH_METHODS.includes(upCasedMethod)) {
    config.body =
      contentType !== CONTENT_TYPES.JSON ? body : JSON.stringify(body);
  }
  return config;
};

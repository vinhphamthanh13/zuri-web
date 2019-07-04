import fetch from 'node-fetch';
import { get } from 'lodash';
import { generateHeaders } from 'utils/http';
import { HTTP_STATUS, CONTENT_TYPE, HTTP_METHOD } from 'constants/http';

export const getRequestToken = headers => {
  const auth = get(headers, 'authorization');
  return auth ? auth.replace(/\w+\s(\w+)/, '$1') : '';
};

/**
 * Util for fetch POST
 * @param {String} url
 * @param {Object} body
 * @param {String} token
 */
export const postApi = (url, body, token) =>
  fetch(url, {
    method: HTTP_METHOD.POST,
    headers: generateHeaders(token),
    body: JSON.stringify(body),
  });

export const fetchApi = (url, param, headersOptions = null) =>
  fetch(`${url}/${param}`, {
    method: HTTP_STATUS.GET,
    headers: headersOptions,
  });

const handleResponse = async (response, defaultResponse) => {
  if (response) {
    const { status } = response;
    const data = await response.json();
    return {
      ...data,
      status,
    };
  }
  return defaultResponse;
};

const handleError = response => {
  const { errors } = response;
  if (errors) {
    return errors;
  }
  return { message: 'Cannot connect to service' };
};

export const handleRequest = async (reqFunction, args, defaultResponse) => {
  try {
    const result = await handleResponse(
      await reqFunction(...args),
      defaultResponse,
    );
    const { status } = result;
    if (status === HTTP_STATUS.OK) {
      return [result, null];
    }

    return [null, result];
  } catch (e) {
    return [null, handleError(e)];
  }
};

export const errorHandle = (res, err) => {
  res.status(500);
  res.set('Content-Type', CONTENT_TYPE.JSON);
  res.send(
    JSON.stringify({
      errors: [
        {
          code: 'INTERNAL ERROR',
          message: `Cannot create application: ${err.message}`,
          severity: 'ERROR',
        },
      ],
    }),
  );
};

export const resultHandle = (res, result) => {
  const status =
    result.status === HTTP_STATUS.UNPROCESSABLE_ENTITY
      ? HTTP_STATUS.UNAUTHORIZED
      : result.status;

  if (status) {
    res.status(status);
  }
  if (status === HTTP_STATUS.UNAUTHORIZED) {
    res.send({
      errors: [result.data],
    });
    return;
  }

  res.send(result.data);
};

/**
 *
 * @param {Object} err {status = 500, message}
 * @param {Object} req
 * @param {Object} res
 * @param {Function} next
 */
export const errorStatusMiddleware = (err, req, res) => {
  const { status = 500, message } = err;

  res.status(status).send({
    message,
  });
};

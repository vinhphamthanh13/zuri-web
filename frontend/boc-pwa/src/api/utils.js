// import { get } from 'lodash';
import { HTTP_STATUS, CONTENT_TYPE } from 'constants/http';
//
// export const getRequestToken = headers => {
//   const auth = get(headers, 'authorization');
//   return auth ? auth.replace(/\w+\s(\w+)/, '$1') : '';
// };

const handleResponse = (response, defaultResponse) => {
  if (response) {
    const { status, data } = response;
    return {
      data,
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
    const result = handleResponse(await reqFunction(...args), defaultResponse);
    const { status } = result;
    if (status === HTTP_STATUS.OK) {
      return [result, null];
    }

    return [null, result];
  } catch (e) {
    return [null, handleError(e)];
  }
};

export const handleNodeServerError = (response, err) => {
  response.status(500);
  response.set('Content-Type', CONTENT_TYPE.JSON);
  response.send(
    JSON.stringify({
      errors: [
        {
          code: 'INTERNAL ERROR',
          message: err.message,
          severity: 'ERROR',
        },
      ],
    }),
  );
};

export const handleNodeServerResponse = (response, result) => {
  const status =
    result.status === HTTP_STATUS.UNPROCESSABLE_ENTITY
      ? HTTP_STATUS.UNAUTHORIZED
      : result.status;

  if (status) {
    response.status(status);
  }
  if (status === HTTP_STATUS.UNAUTHORIZED) {
    response.send({
      errors: [result.data],
    });
    return;
  }

  response.send(result.data);
};

export const errorStatusMiddleware = (err, req, res) => {
  const { status = 500, message } = err;

  res.status(status).send({
    message,
  });
};

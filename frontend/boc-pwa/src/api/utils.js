import { get } from 'lodash';
import { HTTP_STATUS, CONTENT_TYPE } from 'constants/http';

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
  return {
    status: HTTP_STATUS.INTERNAL_ERROR,
    data: {
      message: 'Hiện tại không thể kết nối với máy chủ BOC.',
      success: false,
    },
  };
};

export const handleRequest = async (reqFunction, args, defaultResponse) => {
  try {
    const result = handleResponse(await reqFunction(...args), defaultResponse);
    const status = get(result, 'status');
    const success = get(result, 'data.success');
    if (status === HTTP_STATUS.OK && success) {
      return [result, null];
    }
    return [null, result];
  } catch (error) {
    return [null, handleError(error)];
  }
};

export const handleNodeServerError = (response, error) => {
  const message = get(error, 'data.message');
  const success = get(error, 'data.success') || false;
  const status = get(error, 'status') || HTTP_STATUS.INTERNAL_ERROR;
  response.status(status);
  response.set('Content-Type', CONTENT_TYPE.JSON);
  response.send({
    message,
    success,
  });
};

export const handleNodeServerResponse = (response, result) => {
  const { status, data } = result;
  response.status(status);

  if (
    status === HTTP_STATUS.UNAUTHORIZED ||
    status === HTTP_STATUS.UNPROCESSABLE_ENTITY ||
    status === HTTP_STATUS.ACCESS_DENIED ||
    status === HTTP_STATUS.INTERNAL_ERROR
  ) {
    response.send({
      errors: [
        {
          code: HTTP_STATUS.INTERNAL_ERROR,
          message: data,
        },
      ],
    });
    return;
  }

  response.send(data);
};

export const errorStatusMiddleware = (err, req, res) => {
  const { status = HTTP_STATUS.INTERNAL_ERROR, message } = err;

  res.status(status).send({
    message,
  });
};

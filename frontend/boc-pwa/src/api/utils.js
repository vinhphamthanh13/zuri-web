import { get } from 'lodash';
import { DATA } from 'constants/common';
import { HTTP_STATUS, CONTENT_TYPE } from 'constants/http';

const handleResponse = (response, defaultResponse) => {
  if (response) {
    const { status, data } = response;
    const dataCode = { ...data, code: status };
    return {
      data: dataCode,
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
      code: HTTP_STATUS.INTERNAL_ERROR,
      message:
        'Kết nối với máy chủ BOCVN không thực hiện được. Vui lòng thử lại sau!',
      success: false,
    },
  };
};

export const handleRequest = async (reqFunction, args, defaultResponse) => {
  try {
    const result = handleResponse(await reqFunction(...args), defaultResponse);
    const status = get(result, 'status');
    const success = get(result, DATA.SUCCESS);
    if (status === HTTP_STATUS.OK && success) {
      return [result, null];
    }
    return [null, result];
  } catch (error) {
    return [null, handleError(error)];
  }
};

export const handleNodeServerError = (response, error) => {
  const message = get(error, DATA.MESSAGE);
  const success = get(error, DATA.SUCCESS) || false;
  const status = get(error, 'status') || HTTP_STATUS.INTERNAL_ERROR;
  response.status(status);
  response.set('Content-Type', CONTENT_TYPE.JSON);
  response.send({
    code: status,
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

export const HTTP_STATUS = {
  OK: 200,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  ACCESS_DENIED: 403,
  NOT_FOUND: 404,
  PAYLOAD_TOO_LARGE: 413,
  UNPROCESSABLE_ENTITY: 422,
  INTERNAL_ERROR: 500,
};

export const CONTENT_TYPE = {
  JSON: 'application/json',
  URL_ENCODED: 'application/x-www-form-urlencoded',
};

export const HTTP_METHOD = {
  POST: 'POST',
  GET: 'GET',
};

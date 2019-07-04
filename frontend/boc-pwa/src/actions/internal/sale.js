import template from 'lodash/template';

import { SALE, MESSAGE, ERROR_UPLOAD_CODES } from 'constants/internal';
import { CONTENT_TYPES } from 'constants/common';
import { HTTP_STATUS } from 'constants/http';

import { fetchConfig } from './fetchConfig';
import { loading, loaded, error, alerts } from './app';
import history from '../../history';

export function uploadSaleFailed(errors) {
  return {
    type: SALE.UPLOAD_SALES_FAILED,
    errors,
  };
}

export function uploadSaleSuccess(data) {
  return {
    type: SALE.UPLOAD_SALES_SUCCESS,
    data,
  };
}

export const uploadSale = file => async dispatch => {
  try {
    dispatch(loading());
    const form = new FormData();
    form.append('file', file);
    const res = await fetch(
      `${process.env.JAVA_API}/auth/v1.0/sale/upload`,
      fetchConfig(
        { method: 'POST', contentType: CONTENT_TYPES.MULTIPART_FORM },
        form,
      ),
    );
    const { data, code } = await res.json();

    if (res.ok && code === 0) {
      const message = template(MESSAGE.UPLOAD_SALE_SUCCESS)(data);

      dispatch(uploadSaleSuccess(data));
      dispatch(alerts.success(message));
      return;
    }

    if (code === ERROR_UPLOAD_CODES.INVAILD_FORMAT) {
      dispatch(alerts.warn(MESSAGE.INVAILD_FORMAT));
      return;
    }

    if (res.status === HTTP_STATUS.UNAUTHORIZED) {
      dispatch(alerts.warn(MESSAGE.UNAUTHORIZED));
      history.push('/internal/login');
      localStorage.removeItem('internalAuth');
      return;
    }

    if (res.status === HTTP_STATUS.PAYLOAD_TOO_LARGE) {
      dispatch(alerts.warn(MESSAGE.FILE_TOO_LARGE));
      return;
    }

    if (res.status === HTTP_STATUS.INTERNAL_ERROR) {
      dispatch(alerts.warn(MESSAGE.INTERNAL_ERROR));
      return;
    }

    dispatch(uploadSaleFailed(code));
    let message = 'Unknown';
    if (code) message = code;
    dispatch(alerts.warn(MESSAGE.UPLOAD_SALE_FAILED + message));
  } catch (e) {
    dispatch(error(e, e.message));
  } finally {
    dispatch(loaded());
  }
};

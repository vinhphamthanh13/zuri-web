import { SALE, DEFAULT_STATE } from 'constants/internal';

export default function sales(state = {}, action) {
  const { type, data } = action;
  switch (type) {
    case SALE.UPLOAD_SALES_SUCCESS:
      return data;
    case SALE.UPLOAD_SALES_FAILED:
      return DEFAULT_STATE.auth;
    default:
      return state;
  }
}

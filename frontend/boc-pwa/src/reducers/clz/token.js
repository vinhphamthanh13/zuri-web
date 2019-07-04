import { STORE_TRACKING_TOKEN } from 'constants/clz';

export function trackingToken(state = '', action) {
  if (action.type === STORE_TRACKING_TOKEN) {
    return action.payload;
  }

  return state;
}

export default function token() {
  return;
}

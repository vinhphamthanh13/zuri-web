import { CLZ_SET_LOADING } from 'constants/clz';
import { LOADER_STATUS } from 'constants/common';

export default function loading(state, action) {
  const newState = state || LOADER_STATUS.OFF;

  if (action.type === CLZ_SET_LOADING) {
    return action.payload;
  }

  return newState;
}

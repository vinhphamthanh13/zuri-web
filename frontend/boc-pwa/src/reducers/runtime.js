import { SET_RUNTIME_VARIABLE } from '../constants';

export default function runtime(state, action) {
  const newState = state || {};

  if (action.type === SET_RUNTIME_VARIABLE) {
    return {
      ...newState,
      [action.payload.name]: action.payload.value,
    };
  }

  return newState;
}

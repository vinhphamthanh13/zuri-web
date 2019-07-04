import { SET_LOADER_STATUS } from 'constants/saRegister';

export function loading(state = false, action ) {
  const { type, status } = action;

  if(type === SET_LOADER_STATUS) {
    return status;
  }

  return state;
}

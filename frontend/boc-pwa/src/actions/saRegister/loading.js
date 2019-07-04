import { SET_LOADER_STATUS } from 'constants/saRegister';

export function setLoaderStatus(status) {
  return {
    type: SET_LOADER_STATUS,
    status,
  };
}

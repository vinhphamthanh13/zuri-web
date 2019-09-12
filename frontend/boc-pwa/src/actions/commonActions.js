export const SET_LOADING = 'COMMON.SET_LOADING';
export const SET_ERROR = 'COMMON.SET_ERROR';
export const SET_SUCCESS = 'COMMON.SET_SUCCESS';
export const RESET_ERROR = 'COMMON.RESET_ERROR';
export const RESET_SUCCESS = 'COMMON.RESET_SUCCESS';
export const LOADING = {
  ON: true,
  OFF: false,
};

export const setLoading = payload => ({
  type: SET_LOADING,
  payload,
});
export const setError = payload => ({
  type: SET_ERROR,
  payload,
});
export const setSuccess = payload => ({
  type: SET_SUCCESS,
  payload,
});
export const resetError = () => ({
  type: RESET_ERROR,
});
export const resetSuccess = () => ({
  type: RESET_SUCCESS,
});

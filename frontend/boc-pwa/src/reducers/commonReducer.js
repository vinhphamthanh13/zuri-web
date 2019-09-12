import {
  SET_LOADING,
  SET_ERROR,
  RESET_ERROR,
  SET_SUCCESS,
  RESET_SUCCESS,
} from 'actions/commonActions';

const initState = {
  isLoading: null,
  isError: null,
  errorMessage: '',
  isSuccess: null,
  successMessage: '',
};

const reducer = (state = initState, action) => {
  if (action.type === SET_LOADING) {
    return {
      ...state,
      isLoading: action.payload,
    };
  }
  if (action.type === SET_ERROR) {
    return {
      ...state,
      isError: true,
      errorMessage: action.payload,
    };
  }
  if (action.type === SET_SUCCESS) {
    return {
      ...state,
      isSuccess: true,
      successMessage: action.payload,
    };
  }
  if (action.type === RESET_ERROR) {
    return {
      ...state,
      isError: null,
      errorMessage: '',
    };
  }
  if (action.type === RESET_SUCCESS) {
    return {
      ...state,
      isSuccess: null,
      successMessage: '',
    };
  }

  return state;
};

export default reducer;

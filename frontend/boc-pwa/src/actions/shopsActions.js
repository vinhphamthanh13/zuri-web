/*
 * Naming Rule
 *  1. Request to node server action: nodeActionName
 *  2. Action consumes request: nodeActionNameApi
 *  3. Redux action: actionNameAction
 */

import { get } from 'lodash';
import { LOADING, setError, setLoading } from 'actions/common';
import { NODE_SERVER_URL, PROXY_SHOP } from 'actions/constants';
import { handleRequest } from 'api/utils';
import axios from 'axios';
import { DATA } from 'constants/common';

// Action API to Node Server
const nodeGettingStore = (id, token) =>
  axios.get(`${PROXY_SHOP}${NODE_SERVER_URL.GETTING_STORE}/${id}/${token}`);

// Redux constants
export const SELECTED_SHOP_ID = 'AUTH.SELECTED_SHOP_ID';
export const GETTING_STORE = 'AUTH.GETTING_STORE';

// Redux action
export const selectedShopIdAction = payload => ({
  type: SELECTED_SHOP_ID,
  payload,
});
export const gettingStoreAction = payload => ({
  type: GETTING_STORE,
  payload,
});

// Consuming actions
export const nodeGettingStoreApi = (id, token) => async dispatch => {
  dispatch(setLoading(LOADING.ON));
  const [result, error] = await handleRequest(nodeGettingStore, [id, token]);
  if (error) {
    const message = get(error, DATA.MESSAGE);
    dispatch(setError(message));
  } else {
    const gettingShopInfo = get(result, DATA.OBJECT);
    dispatch(gettingStoreAction(gettingShopInfo));
  }
  dispatch(setLoading(LOADING.OFF));
};

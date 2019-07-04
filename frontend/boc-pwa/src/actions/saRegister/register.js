import { post } from 'utils/http';
import { HTTP_STATUS } from 'constants/http';
import fetch from 'node-fetch';
import { MESSAGE, SA_ALERT } from 'constants/saRegister';
import { URL } from 'constants/api';

import { getToken } from './utils';
import { alert } from './alert';
import { updateUserInfo } from './auth';

/**
 *
 * @param {Object} registerInfo
 */
export const registerDevice = registerInfo => async dispatch => {
  const { phoneNumber, retailAgent, sessionID, verificationID } = registerInfo;

  try {
    const saToken = getToken();
    const response = await post(
      URL.SA_REGISTER_DEVICE,
      {
        phoneNumber,
        retailAgent,
        sessionID,
        verificationID,
      },
      saToken,
    );
    const result = await response.json();

    if (!response.ok || result.message) {
      throw new Error('Can not register devices');
    }
    const { createAt } = result.data;

    dispatch(
      updateUserInfo({
        createAt,
      }),
    );
    return {
      data: {
        createAt,
      },
    };
  } catch (err) {
    dispatch(
      alert({
        title: SA_ALERT.error.title,
        message: MESSAGE.REGISTER_FAIL,
      }),
    );

    return Promise.reject(new Error(err));
  }
};

export const getSAInfo = () => async dispatch => {
  try {
    const saToken = getToken();
    const response = await fetch(URL.SA_GET_INFO, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${saToken}`,
      },
    });

    const result = await response.json();

    // For case backend return 404 NOT FOUND set createAt to null
    if (response.status === HTTP_STATUS.NOT_FOUND) {
      dispatch(
        updateUserInfo({
          createAt: null,
        }),
      );
      return null;
    }

    const { createAt } = result.data;

    dispatch(
      updateUserInfo({
        createAt,
      }),
    );

    return { createAt };
  } catch (err) {
    dispatch(
      alert({
        title: SA_ALERT.error.title,
        message: MESSAGE.CHECK_SA_FAIL,
      }),
    );

    return null;
  }
};

export const unRegisterDevice = () => async dispatch => {
  try {
    const saToken = getToken();
    await fetch(URL.SA_UNREGISTER_DEVICE, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${saToken}`,
      },
    });

    dispatch(
      updateUserInfo({
        createAt: null,
      }),
    );
  } catch (err) {
    dispatch(
      alert({
        title: SA_ALERT.error.title,
        message: MESSAGE.UNREGISTER_FAIL,
      }),
    );
  }
};

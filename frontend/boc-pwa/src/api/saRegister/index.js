import isEmpty from 'lodash/isEmpty';
import storage from 'node-persist';
import fetch from 'node-fetch';

import { errorStatusMiddleware, postApi } from '../utils';
import config from '../../config';
import { getCashOffer } from '../customerOffer/getCashOffer';
import { getFakeApplicationBody } from './constant';
import { ACL_SALE_APP } from '../constant';

const router = require('express').Router();

const { url } = config.auth.oapi;
const CREATE_APPLICATION_API = '/financing/v1/applications';

router.post('/login', async (req, res, next) => {
  const { username, password } = req.body;

  try {
    const response = await postApi(`${ACL_SALE_APP}/token`, {
      username,
      password,
    });
    const result = await response.json();

    if (!response.ok) {
      const error = {
        status: response.status,
        message: result.message || result.detailMessage,
      };

      throw error;
    }

    res.send({ data: result.data });
  } catch (err) {
    if (isEmpty(err.message)) err.message = 'Error occur while Login';
    next(err);
  }
});

router.post('/register', async (req, res, next) => {
  const { phoneNumber, retailAgent, sessionID, verificationID } = req.body;
  const tokenJava = req.header('Authorization').split(' ')[1];

  try {
    const token = await storage.getItem('clz_token');
    const offerList = await getCashOffer(30, false);

    const body = getFakeApplicationBody({
      phoneNumber,
      retailAgent,
      sessionID,
      verificationID,
      bestOffer: offerList.data.data[0],
    });

    // Submit bsl
    const responseBSL = await postApi(
      `${url}${CREATE_APPLICATION_API}`,
      body,
      token,
    );
    const application = await responseBSL.json();
    if (application.errors || !responseBSL.ok) {
      const { errors } = application;
      const error = {
        status: responseBSL.status,
        message: errors.message,
      };

      throw error;
    }

    // call Java API
    const responseJava = await postApi(
      `${ACL_SALE_APP}/auth/register`,
      {
        contractNumber: application.applicationCode,
        phoneNumber,
        sessionId: sessionID,
        valid: true,
      },
      tokenJava,
    );
    const resultJava = await responseJava.json();
    if (!responseJava.ok) {
      const error = {
        status: responseJava.status,
        message: resultJava.message || resultJava.detailMessage,
      };

      throw error;
    }

    res.send({
      data: { createAt: resultJava.data.createAt },
    });
  } catch (err) {
    if (isEmpty(err.message)) err.message = 'Error occur while Register';
    next(err);
  }
});

router.get('/sa', async (req, res, next) => {
  const authHeader = req.header('Authorization');

  try {
    const response = await fetch(`${ACL_SALE_APP}/auth/check`, {
      method: 'GET',
      headers: {
        Authorization: authHeader,
      },
    });

    const result = await response.json();
    if (!response.ok || result.message) {
      const error = {
        status: response.status,
        message: result.message || result.detailMessage,
      };

      throw error;
    }

    res.send({ data: result.data });
  } catch (err) {
    if (isEmpty(err.message)) err.message = 'Error occur while check SA info';
    next(err);
  }
});

router.delete('/unregister', async (req, res, next) => {
  const authHeader = req.header('Authorization');

  try {
    const response = await fetch(`${ACL_SALE_APP}/auth/unregister`, {
      method: 'DELETE',
      headers: {
        Authorization: authHeader,
      },
    });

    const result = await response.json();
    if (!response.ok || result.message) {
      const error = {
        status: response.status,
        message: result.message || result.detailMessage,
      };

      throw error;
    }

    res.send({
      data: { success: true },
    });
  } catch (err) {
    if (isEmpty(err.message))
      err.message = 'Error occur while unregister device';
    next(err);
  }
});

router.use(errorStatusMiddleware);

export default router;

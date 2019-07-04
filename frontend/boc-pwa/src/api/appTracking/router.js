import express from 'express';
import { get } from 'lodash';
import { URL, TRACKING_URL } from './constant';
import {
  postApi,
  resultHandle,
  errorHandle,
  handleRequest,
  getRequestToken,
} from '../utils';

const router = express.Router();

// Tracking App by URL
const appTracking = url => {
  router.post(`/${URL[url]}`, async (req, res) => {
    const { body, headers } = req;
    const token = getRequestToken(headers);
    try {
      const [result, error] = await handleRequest(postApi, [
        TRACKING_URL[url],
        body,
        token,
      ]);
      if (error) {
        console.error(`Error when tracking ${URL[url]}`, error);
      }

      resultHandle(res, result);
    } catch (err) {
      errorHandle(res, err);
    }
  });
};

// Middleware to dispatch the URL
router.use('/', (req, res, next) => {
  const url = get(req, 'url');
  appTracking(url.replace('/', ''));
  next();
});

export default router;

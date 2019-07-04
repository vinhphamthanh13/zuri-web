import express from 'express';
import UserService from './UserService';
import { resultHandle, errorHandle } from '../utils';

const router = express.Router();

router.post('/auth', async (req, res) => {
  const {
    body: { username, password },
  } = req;

  try {
    const result = await UserService.login(username, password);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
});

export default router;

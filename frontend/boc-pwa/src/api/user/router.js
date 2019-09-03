import express from 'express';
import UserService from './UserService';
import { handleNodeServerResponse, handleNodeServerError } from '../utils';

const router = express.Router();

router.post('/auth', async (req, res) => {
  const {
    body: { username, password },
  } = req;

  try {
    const result = await UserService.login(username, password);
    handleNodeServerResponse(res, result);
  } catch (err) {
    handleNodeServerError(res, err);
  }
});

export default router;

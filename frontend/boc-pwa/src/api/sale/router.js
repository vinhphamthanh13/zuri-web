import fetch from 'node-fetch';
import express from 'express';
import Multer from 'multer';
import SaleService from './SaleService';
import config from '../../config';
import authApiKey from '../middlewares/authApiKey';
import authToken from '../middlewares/authInternalToken';
import { resultHandle, errorHandle, handleRequest } from '../utils';
import { getDb } from '../dbs';

const router = express.Router();
const multer = Multer({
  storage: Multer.memoryStorage(),
  limits: {
    fileSize: 5 * 1024 * 1024, // no larger than 5mb, you can change as needed.
  },
});

const uploadSaleFile = async (req, res) => {
  try {
    const { user } = req;
    const saleService = new SaleService(await getDb());
    const result = await saleService.importFile(req.file, user);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};

const checkCode = code =>
  fetch(`${config.api.javaAPI}/v1.0/sale/check/${code}`);

router.get('/verify/:saleCode', async (req, res) => {
  const { saleCode } = req.params;
  try {
    const [result, error] = await handleRequest(checkCode, [saleCode]);
    if (error) {
      errorHandle(res, error);
    } else {
      resultHandle(res, result);
    }
  } catch (err) {
    errorHandle(res, err);
  }
});

router.post('/', authApiKey, async (req, res) => {
  const saleDto = req.body;
  try {
    const saleService = new SaleService(await getDb());
    const result = await saleService.addSale(saleDto);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
});

router.post('/upload', authApiKey, multer.single('file'), uploadSaleFile);
router.post(
  '/internal/upload',
  authToken,
  multer.single('file'),
  uploadSaleFile,
);

export default router;

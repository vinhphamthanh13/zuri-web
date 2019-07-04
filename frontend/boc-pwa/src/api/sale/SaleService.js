import { object, string, array } from 'yup';
import path from 'path';

import { HTTP_STATUS } from 'constants/http';
import { ERROR_CODES, UPLOAD_STATUS } from './constant';
import ResponseDto from '../ResponseDto';
import SaleModel from './SaleModel';
import VerifySaleDto from './VerifySaleDto';
import CsvConverter from './CsvConverter';
import ExcelConverter from './ExcelConverter';
import UploadTrackingService from './UploadTrackingService';

const BatchSize = 50;
const SaleDocName = 'sales';
const SupportedFiles = ['.csv', '.xlsx'];
const SALE_SCHEMA = array().of(
  object().shape({
    shopCode: string().required(),
    dsmMail: string()
      .email()
      .required(),
    code: string().required(),
    name: string(),
    mail: string()
      .email()
      .required(),
  }),
);

class SaleService {
  constructor(db) {
    this.db = db;
    this.trackingService = new UploadTrackingService(this.db);
  }

  async verifySaleCode(saleCode) {
    if (!saleCode) {
      return new ResponseDto(null, HTTP_STATUS.BAD_REQUEST);
    }

    const sales = this.db.collection(SaleDocName);
    const sale = await sales.findOne({ code: saleCode });
    if (sale) {
      const { name, phoneNumber, mail } = sale;
      return new ResponseDto(
        new VerifySaleDto({ isValid: true, name, phoneNumber, mail }),
      );
    }

    return new ResponseDto(new VerifySaleDto({ isValid: false }));
  }

  async addSale(saleDto) {
    if (!saleDto || !saleDto.code) {
      return new ResponseDto(
        {
          errors: [ERROR_CODES.validatationFailed],
        },
        HTTP_STATUS.BAD_REQUEST,
      );
    }

    const sales = this.db.collection(SaleDocName);
    const sale = new SaleModel(saleDto);
    sale.updateAt = new Date().getTime();
    sales.updateOne({ code: sale.code }, { $set: sale }, { upsert: true });

    return new ResponseDto(sale);
  }

  async importFile(file, auth) {
    const tracking = {
      fileName: file.originalname,
      status: UPLOAD_STATUS.failed,
      note: ERROR_CODES.inValidFormat.message,
    };
    if (!file || file.buffer.length === 0) {
      tracking.note = ERROR_CODES.noContent.message;
      await this.trackingService.add(tracking, auth);
      return new ResponseDto(
        { errors: [ERROR_CODES.noContent] },
        HTTP_STATUS.BAD_REQUEST,
      );
    }
    tracking.fileSize = file.buffer.length;
    const fileExt = path.extname(file.originalname);
    if (!SupportedFiles.includes(fileExt)) {
      tracking.note = ERROR_CODES.wrongType.message;
      await this.trackingService.add(tracking, auth);
      return new ResponseDto(
        { errors: [ERROR_CODES.wrongType] },
        HTTP_STATUS.BAD_REQUEST,
      );
    }

    let processService = new ExcelConverter();
    if (fileExt === '.csv') {
      processService = new CsvConverter();
    }

    const importResult = await processService.convertToJson(file);
    if (importResult.errors.length === 0) {
      const importSales = importResult.data;
      const isVaildFormat = await SALE_SCHEMA.isValid(importSales);
      if (!isVaildFormat) {
        await this.trackingService.add(tracking, auth);
        return new ResponseDto(
          { errors: [ERROR_CODES.inValidFormat] },
          HTTP_STATUS.BAD_REQUEST,
        );
      }
      const numberImported = await this.importData(importResult.data);
      tracking.status = UPLOAD_STATUS.success;
      tracking.numberOfRecord = numberImported;
      tracking.note = UPLOAD_STATUS.success;
      await this.trackingService.add(tracking, auth);

      return new ResponseDto({ numberImportedRecords: numberImported });
    }

    await this.trackingService.add(tracking, auth);

    return new ResponseDto(
      { errors: importResult.errors },
      HTTP_STATUS.BAD_REQUEST,
    );
  }

  async importData(importSales) {
    // step 1 : truncate temp docs
    const tmpSales = this.db.collection(`${SaleDocName}_temp`);
    tmpSales.deleteMany({});
    // step 2: insert to temp docs
    let start = 0;
    let batches = [];
    let total = 0;

    const processedResults = [];
    importSales.forEach(item => {
      const sale = new SaleModel(item);
      batches.push(sale);
      start += 1;
      if (start >= BatchSize) {
        const tempDatas = [...batches];
        processedResults.push(tmpSales.insertMany(tempDatas));
        batches = [];
        total += start;
        start = 0;
      }
    });

    await Promise.all(processedResults);
    // insert rest items
    if (batches.length > 0) {
      total += start;
      await tmpSales.insertMany(batches);
    }
    // step3: backup sales, rename tempSales to sales
    const sales = this.db.collection(SaleDocName);
    try {
      await sales.rename(`${SaleDocName}_backup`, { dropTarget: true });
    } catch (e) {
      // do nothing, in case sale doc is not exist
    }
    await tmpSales.rename(SaleDocName, { dropTarget: true });

    return total;
  }
}

export default SaleService;

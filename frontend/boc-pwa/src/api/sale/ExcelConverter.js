import _ from 'lodash';
import readXlsxFile from 'read-excel-file/node';
import convertToJson from 'read-excel-file/json';
import stream from 'stream';

import { ERROR_CODES, SALE_HEADERS } from './constant';
import ConvertResult from './ConvertResult';

const SALE_SCHEMA = {
  SHOP_CODE: {
    prop: 'shopCode',
    type: String,
    required: true,
  },
  SHOP_NAME: {
    prop: 'shopName',
    type: String,
  },

  SHOP_ADRESS: {
    prop: 'shopAddress',
    type: String,
  },

  SHOP_DISTRICT: {
    prop: 'shopDistrict',
    type: String,
  },

  SHOP_PROVINCE: {
    prop: 'shopProvince',
    type: String,
  },

  SHOP_AREA: {
    prop: 'shopArea',
    type: String,
  },

  DSM_MANAGE_NAME: {
    prop: 'dsmName',
    type: String,
  },

  DSM_MAIL: {
    prop: 'dsmMail',
    type: String,
  },

  SA_CODE: {
    prop: 'code',
    type: String,
    required: true,
  },

  SA_NAME: {
    prop: 'name',
    type: String,
  },
  SA_MAIL: {
    prop: 'mail',
    type: String,
    required: true,
  },
  SA_PHONE: {
    prop: 'phoneNumber',
    type: String,
  },
};

class ExcelConverter {
  constructor(hearders, schema) {
    this.hearders = hearders || SALE_HEADERS;
    this.schema = schema || SALE_SCHEMA;
  }

  async convertToJson(file) {
    const result = new ConvertResult([ERROR_CODES.inValidFormat]);
    try {
      if (!file || file.buffer.length === 0) {
        return result;
      }

      const bufferStream = new stream.PassThrough();
      bufferStream.end(file.buffer);
      const dataResult = await readXlsxFile(bufferStream);
      if (dataResult.length === 0 || !_.isEqual(dataResult[0], this.hearders)) {
        return result;
      }

      const jsonResult = await convertToJson(dataResult, this.schema, {
        properties: dataResult.properties,
      });

      if (jsonResult.errors.length === 0) {
        result.errors = [];
        result.data = jsonResult.rows;

        return result;
      }
      console.error(result.errors);

      return result;
    } catch (err) {
      console.error(err);

      return result;
    }
  }
}

export default ExcelConverter;

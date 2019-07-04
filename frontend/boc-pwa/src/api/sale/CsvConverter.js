import csv from 'csvtojson';
import _ from 'lodash';

import { ERROR_CODES, SALE_HEADERS } from './constant';
import ConvertResult from './ConvertResult';

const SALE_JSON_HEADERS = [
  'shopCode',
  'shopName',
  'shopAddress',
  'shopDistrict',
  'shopProvince',
  'shopArea',
  'dsmName',
  'dsmMail',
  'code',
  'name',
  'mail',
  'phoneNumber',
];

class CsvConverter {
  constructor(hearders, jsonHeaders) {
    this.hearders = hearders || SALE_HEADERS;
    this.jsonHeaders = jsonHeaders || SALE_JSON_HEADERS;
  }

  async convertToJson(file) {
    const result = new ConvertResult([ERROR_CODES.inValidFormat]);
    try {
      if (!file || file.buffer.length === 0) {
        return result;
      }

      const data = file.buffer.toString('utf8');
      const headersResults = CsvConverter.getHeadersFromCSV(data);
      if (!_.isEqual(headersResults, this.hearders)) {
        return result;
      }

      const jsonResults = await CsvConverter.convertCsvToJsons(
        data,
        this.jsonHeaders,
      );
      if (!jsonResults || jsonResults.length === 0) {
        result.errors = [ERROR_CODES.noContent];
      }

      result.errors = [];
      result.data = jsonResults;

      return result;
    } catch (err) {
      console.error(err);

      return result;
    }
  }

  static getHeadersFromCSV(data) {
    const headersString = data.split(/\r?\n/)[0];

    return _.chain(headersString)
      .split(',')
      .map(e => _.trim(e))
      .value();
  }

  static async convertCsvToJsons(data, headers) {
    const results = await csv({
      noheader: false,
      trim: true,
      headers,
    }).fromString(data);

    return results;
  }
}

export default CsvConverter;

import { isNumber } from 'lodash';

export const formatCurrency = value => {
  const stringValue = isNumber(value) ? value.toString() : value;
  return `${stringValue.replace(/(\d)(?=(\d{3})+(?!\d))/g, `$1.`)} VNĐ`;
};

export const formatStringLength = (value, length) =>
  value.toString().length > length
    ? `${value.toString().substr(0, length)}...`
    : value;

import { isNumber } from 'lodash';

export const formatCurrency = value => {
  const stringValue = isNumber(value) ? value.toString() : value;
  return `${stringValue.replace(/(\d)(?=(\d{3})+(?!\d))/g, `$1.`)} VNĐ`;
};

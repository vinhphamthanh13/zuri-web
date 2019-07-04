import { chunk } from 'lodash';
import moment from 'moment';
import {
  THIS_MONTH,
  THIS_YEAR,
  CALENDAR_WEEKS,
  DAYS_OF_WEEK,
} from './constants';

export const zeroPad = (value, length) => `${value}`.padStart(length, '0');

export const getPreviousMonth = (month, year) => {
  const [previousMonth, previousYear] =
    +month > 1 ? [+month - 1, year] : [12, +year - 1];
  return { month: previousMonth, year: previousYear };
};

export const getNextMonth = (month, year) => {
  const [nextMonth, nextYear] =
    +month < 12 ? [+month + 1, year] : [1, +year + 1];
  return { month: nextMonth, year: nextYear };
};

export default (month = THIS_MONTH, year = THIS_YEAR) => {
  // First day and number of days in month
  const daysOfMonth = moment(`${year}-${month}`, 'YYYY-MM').daysInMonth();

  // Fill out day to 6 weeks
  const daysOfPreviousMonth = moment(`${year}-${zeroPad(month, 2)}`)
    .startOf('month')
    .day();
  const daysOfNextMonth =
    CALENDAR_WEEKS * 7 - (daysOfPreviousMonth + daysOfMonth);

  // Get the previous and next month and year
  const { month: previousMonth, year: previousYear } = getPreviousMonth(
    month,
    year,
  );
  const { month: nextMonth, year: nextYear } = getNextMonth(month, year);

  // Get number of days in previous month
  const previousMonthDays = moment(
    `${previousYear}-${previousMonth}`,
    'YYYY-MM',
  ).daysInMonth();

  // Build dates to be displayed from previous month
  const previousMonthDates = [
    ...Array.from({ length: daysOfPreviousMonth }, (date, index) => [
      previousYear,
      zeroPad(previousMonth, 2),
      zeroPad(index + 1 + (previousMonthDays - daysOfPreviousMonth), 2),
    ]),
  ];

  // Build dates to be displayed in current month
  const currentMonthDates = [
    ...Array.from({ length: daysOfMonth }, (date, index) => [
      year,
      zeroPad(month, 2),
      zeroPad(index + 1, 2),
    ]),
  ];

  // Build dates to be displayed from next month
  const nextMonthDates = [
    ...Array.from({ length: daysOfNextMonth }, (date, index) => [
      nextYear,
      zeroPad(nextMonth, 2),
      zeroPad(index + 1, 2),
    ]),
  ];

  // Combine all dates
  return chunk(
    [...previousMonthDates, ...currentMonthDates, ...nextMonthDates],
    DAYS_OF_WEEK,
  );
};

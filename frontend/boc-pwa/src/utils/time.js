/* eslint-disable no-restricted-syntax, no-bitwise, no-prototype-builtins  */
// import split from 'lodash/split';
import { chain } from 'lodash';

function throwIfInvalidDate(date) {
  if (Object.prototype.toString.call(date, null) !== '[object Date]') {
    throw new Error('The parameter type is incorrect.');
  }
}

function daysInMonth(year, month) {
  return new Date(year, month + 1, 0).getDate();
}

export function convertDate(date, format) {
  let str = format;
  const o = {
    'M+': date.getMonth() + 1,
    'D+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds(),
  };
  if (/(Y+)/.test(format)) {
    str = str.replace(
      RegExp.$1,
      date
        .getFullYear()
        .toString()
        .substr(4 - RegExp.$1.length),
    );
  }

  for (const k in o) {
    if (new RegExp(`(${k})`).test(format)) {
      str = str.replace(
        RegExp.$1,
        RegExp.$1.length === 1
          ? o[k]
          : `00${o[k]}`.substr(o[k].toString().length),
      );
    }
  }

  return str;
}

export function nextYear(now, index = 0) {
  throwIfInvalidDate(now);
  const date = new Date(
    now.getFullYear() + index,
    now.getMonth(),
    now.getDate(),
    now.getHours(),
    now.getMinutes(),
    now.getSeconds(),
  );
  return date;
}

export function nextMonth(now, index = 0) {
  throwIfInvalidDate(now);
  const year = now.getFullYear();
  const month = now.getMonth() + index;
  const dayOfMonth = Math.min(now.getDate(), daysInMonth(year, month));
  const date = new Date(
    year,
    month,
    dayOfMonth,
    now.getHours(),
    now.getMinutes(),
    now.getSeconds(),
  );
  return date;
}

export function nextDate(now, index = 0) {
  throwIfInvalidDate(now);
  const date = new Date(now.getTime() + index * 24 * 60 * 60 * 1000);
  return date;
}

export function nextHour(now, index = 0) {
  throwIfInvalidDate(now);
  const date = new Date(now.getTime() + index * 60 * 60 * 1000);
  return date;
}

export function nextMinute(now, index = 0) {
  throwIfInvalidDate(now);
  const date = new Date(now.getTime() + index * 60 * 1000);
  return date;
}

export function nextSecond(now, index = 0) {
  throwIfInvalidDate(now);
  const date = new Date(now.getTime() + index * 1000);
  return date;
}

export function getTimeType(format) {
  const typeMap = {
    Y: 'Year',
    M: 'Month',
    D: 'Date',
    h: 'Hour',
    m: 'Minute',
    s: 'Second',
  };

  for (const key in typeMap) {
    if (typeMap.hasOwnProperty(key)) {
      if (~format.indexOf(key)) {
        return typeMap[key];
      }
    }
  }
  return null;
}

export function formatDateString(date) {
  return convertDate(new Date(date), 'DD/MM/YYYY');
}

export function formatFullDateString(date) {
  return convertDate(new Date(date), 'DD/MM/YYYY hh:mm');
}

export function getDateFromString(dateString) {
  const date = chain(dateString)
    .split('T')
    .head();

  return new Date(date);
}

export const milisecond = 1000;
export const milisecondOfMinute = milisecond * 60;
export const milisecondOfHour = milisecondOfMinute * 60;
export const milisecondOfDay = milisecondOfHour * 24;
export const milisecondOfWeek = milisecondOfDay * 7;

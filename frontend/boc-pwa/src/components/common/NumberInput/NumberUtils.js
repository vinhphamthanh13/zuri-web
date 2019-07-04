/* eslint-disable no-continue */
import { toString, isNil } from 'lodash';

function escapeRegExp(str) {
  return str.replace(/[-[\]/{}()*+?.\\^$|]/g, '\\$&');
}

function getSeperators(props) {
  let { thousandSeparator, decimalSeperator } = props;
  thousandSeparator === true && (thousandSeparator = ',');
  decimalSeperator &&
    thousandSeparator &&
    (decimalSeperator = thousandSeparator === ',' ? '.' : ',');
  decimalSeperator === true && (decimalSeperator = '.');

  return {
    decimalSeperator,
    thousandSeparator,
  };
}

function getNumberRegex(props, g) {
  const { decimalSeperator } = getSeperators(props);
  return new RegExp(
    `\\d${decimalSeperator ? `|${escapeRegExp(decimalSeperator)}` : ''}`,
    g ? 'g' : undefined,
  );
}

function formatWithPattern(str, props) {
  const { format, mask } = props;
  if (!format) return str;
  const hashCount = format.split('#').length - 1;
  let hashIdx = 0;
  let frmtdStr = format;

  for (let i = 0, ln = str.length; i < ln; i += 1) {
    if (i < hashCount) {
      hashIdx = frmtdStr.indexOf('#');
      frmtdStr = frmtdStr.replace('#', str[i]);
    }
  }

  const lastIdx = frmtdStr.lastIndexOf('#');

  if (mask) {
    return frmtdStr.replace(/#/g, mask);
  }
  return (
    frmtdStr.substring(0, hashIdx + 1) +
    (lastIdx !== -1 ? frmtdStr.substring(lastIdx + 1, frmtdStr.length) : '')
  );
}

export function setCaretPosition(e, caretPos) {
  const el = e.target;
  el.value = el.value;

  // this is used to not only get "focus", but
  // to make sure we don't have it everything -selected-
  // (it causes an issue in chrome, and having it doesn't hurt any other browser)
  if (!isNil(el)) {
    if (el.createTextRange) {
      const range = el.createTextRange();
      range.move('character', caretPos);
      range.select();
      return;
    }
    // (el.selectionStart === 0 added for Firefox bug)
    if (el.selectionStart || el.selectionStart === 0) {
      el.focus();
      el.setSelectionRange(caretPos, caretPos);
      return;
    }

    el.focus();
  }
}

export function getCursorPosition(
  props,
  inputValue,
  formattedValue,
  cursorPos,
) {
  const numRegex = getNumberRegex(props);

  let j = 0;
  for (let i = 0; i < cursorPos; i += 1) {
    if (!inputValue[i].match(numRegex) && inputValue[i] !== formattedValue[j])
      continue;
    while (inputValue[i] !== formattedValue[j] && j < formattedValue.length)
      j += 1;
    j += 1;
  }

  // check if there is no number before caret position
  while (j > 0 && formattedValue[j]) {
    if (!formattedValue[j - 1].match(numRegex)) j -= 1;
    else break;
  }
  return j;
}

export function formatInput(val, props) {
  const { prefix, suffix, format, maxLength } = props;
  const { thousandSeparator, decimalSeperator } = getSeperators(props);

  const numRegex = getNumberRegex(props, true);

  if (!val || !toString(val).match(numRegex))
    return { value: '', formattedValue: '' };
  const num = toString(val)
    .match(numRegex)
    .join('');

  let formattedValue = num;

  if (maxLength > 0 && formattedValue.length > maxLength) {
    formattedValue = formattedValue.substring(0, maxLength);
  }

  if (format) {
    if (typeof format === 'string') {
      formattedValue = formatWithPattern(formattedValue, props);
    } else if (typeof format === 'function') {
      formattedValue = format(formattedValue);
    }
  } else {
    let beforeDecimal = formattedValue;
    let afterDecimal = '';
    const hasDecimals = formattedValue.indexOf(decimalSeperator) !== -1;
    if (decimalSeperator && hasDecimals) {
      const parts = formattedValue.split(decimalSeperator);
      beforeDecimal = parts[0];
      afterDecimal = parts[1];
    }
    if (thousandSeparator) {
      beforeDecimal = beforeDecimal.replace(
        /(\d)(?=(\d{3})+(?!\d))/g,
        `$1${thousandSeparator}`,
      );
    }
    // add prefix and suffix
    if (prefix) beforeDecimal = prefix + beforeDecimal;
    if (suffix) afterDecimal = `${afterDecimal} ${suffix}`;

    formattedValue =
      beforeDecimal + ((hasDecimals && decimalSeperator) || '') + afterDecimal;
  }

  return {
    value: formattedValue.match(numRegex).join(''),
    formattedValue,
  };
}

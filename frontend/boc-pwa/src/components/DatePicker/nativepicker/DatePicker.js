import React, { Component } from 'react';
import { func, string, objectOf, any, bool } from 'prop-types';
import moment from 'moment';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import s from './DatePicker.css';

class DatePicker extends Component {
  static propTypes = {
    maxDate: objectOf(any).isRequired,
    minDate: objectOf(any).isRequired,
    onChange: func.isRequired,
    name: string.isRequired,
    value: string.isRequired,
    touched: bool.isRequired,
    setFieldError: func.isRequired,
    errorMsg: string.isRequired,
    setFieldTouched: func.isRequired,
    label: string.isRequired,
    validationMsg: string,
    requiredMsg: string,
    dateFormat: string.isRequired,
    displayFormat: string.isRequired,
    require: bool,
  };

  static defaultProps = {
    require: false,
    validationMsg: '',
    requiredMsg: '',
  };

  constructor(props) {
    super(props);
    const { value } = props;
    this.state = {
      date: moment(value).isValid() ? value : '',
      errors: {},
    };
  }

  handleOnChange = event => {
    event && event.preventDefault();
    const {
      name,
      onChange,
      setFieldTouched,
      minDate,
      maxDate,
      setFieldError,
      validationMsg,
      dateFormat,
    } = this.props;
    const { value } = event.target;
    const currentDate = moment(value);
    const errors =
      currentDate > maxDate || currentDate < minDate
        ? { [name]: validationMsg }
        : null;
    this.setState({ date: currentDate, errors });
    onChange(name, !errors ? moment(value).format(dateFormat) : '');
    errors && setFieldError(name, errors[name]);
    setFieldTouched(name);
  };

  render() {
    const {
      minDate,
      maxDate,
      name,
      errorMsg,
      touched,
      label,
      requiredMsg,
      dateFormat,
      displayFormat,
      require,
    } = this.props;
    const { date, errors } = this.state;
    let resolveError =
      touched && (errors && errors[name]) ? errors[name] : errorMsg;
    resolveError = resolveError.includes(name) ? requiredMsg : resolveError;
    const calendarStyle =
      (errors && errors[name]) || (touched && resolveError)
        ? `${s.calendar} ${s.calendarError}`
        : s.calendar;
    const isStatusIcon = !errors && !errorMsg && moment(date).isValid();
    const labelStyle =
      date && moment(date).isValid() ? s.dateLabel : s.dateLabelInActive;

    return (
      <div className={s.datepicker}>
        <div className={calendarStyle}>
          <div className={labelStyle}>
            <span>
              {label}
              {require && <sup>(*)</sup>}
            </span>
          </div>
          <div className={s.dateCalendar}>
            <div>
              <i className={`material-icons ${s.icon}`}>date_range</i>
            </div>
            <div className={s.formControl}>
              <input
                type="date"
                name={name}
                value={moment(date).format(dateFormat)}
                onChange={this.handleOnChange}
                min={moment(minDate).format(dateFormat)}
                max={moment(maxDate).format(dateFormat)}
                onKeyDown={e => {
                  e.preventDefault();
                }}
              />
              <div className={s.formValue}>
                {moment(date).isValid()
                  ? moment(date).format(displayFormat)
                  : ''}
              </div>
            </div>
            <div className={s.statusIconWrapper}>
              {isStatusIcon && (
                <i className={`material-icons ${s.statusIcon}`}>check</i>
              )}
            </div>
          </div>
        </div>
        {touched && (
          <div className={s.errorMessage}>
            {resolveError.includes(name) ? requiredMsg : resolveError}
          </div>
        )}
      </div>
    );
  }
}

export default withStyles(s)(DatePicker);

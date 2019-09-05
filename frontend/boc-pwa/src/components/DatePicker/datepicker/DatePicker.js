import React, { Component } from 'react';
import { string, func, bool } from 'prop-types';
import { noop } from 'lodash';
import moment from 'moment';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Input } from 'homecredit-ui';
import Calendar from '../calendar/Calendar';
import s from './DatePicker.css';

class DatePicker extends Component {
  static propTypes = {
    name: string.isRequired,
    touched: bool.isRequired,
    dateFormat: string.isRequired,
    displayFormat: string.isRequired,
    label: string,
    errorMsg: string,
    onChange: func,
    require: bool,
  };

  static defaultProps = {
    errorMsg: '',
    label: '',
    onChange: noop,
    require: false,
  };
  constructor(props) {
    super(props);
    this.state = {
      value: null,
      maxDate: null,
      minDate: null,
      isCalendarOpen: false,
    };
  }

  static getDerivedStateFromProps(props, state) {
    const { value, maxDate, minDate } = props;
    const {
      value: cachedValue,
      maxDate: cachedMaxDate,
      minDate: cachedMinDate,
    } = state;
    if (
      value !== cachedValue ||
      maxDate !== cachedMaxDate ||
      minDate !== cachedMinDate
    ) {
      return {
        value,
        maxDate,
        minDate,
      };
    }

    return null;
  }

  toggleCalendar = () =>
    this.setState({ isCalendarOpen: !this.state.isCalendarOpen });

  handleDateChange = date => {
    const { value } = this.state;
    const { onChange, name } = this.props;
    onChange(name, date);
    if (moment(date).diff(moment(value)) === 0) {
      this.setState({
        value: date,
      });
    }
  };

  render() {
    const {
      name,
      label,
      errorMsg,
      touched,
      require,
      dateFormat,
      displayFormat,
    } = this.props;
    const { value, minDate, maxDate, isCalendarOpen } = this.state;

    return (
      <>
        <div className={s.datepicker}>
          <div onClick={this.toggleCalendar}>
            <Input
              errorMsg={errorMsg}
              touched={touched}
              label={label}
              icon="date_range"
              name={name}
              readOnly
              type="text"
              value={
                (moment(value).isValid() &&
                  moment(value).format(displayFormat)) ||
                ''
              }
              require={require}
              msgPosition="below"
            />
          </div>
        </div>

        {isCalendarOpen && (
          <div className={s.backdrop}>
            <Calendar
              date={moment(value)}
              maxDate={maxDate}
              minDate={minDate}
              onDateChanged={this.handleDateChange}
              onClose={this.toggleCalendar}
              dateFormat={dateFormat}
            />
          </div>
        )}
      </>
    );
  }
}

export default withStyles(s)(DatePicker);

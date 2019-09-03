import React, { Component } from 'react';
import { func, string, objectOf, any } from 'prop-types';
import { chunk, noop, uniqueId } from 'lodash';
import moment from 'moment/moment';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import calendar, { zeroPad } from 'components/DatePicker/calendar';
import {
  WEEK_DATES,
  WEEK_DAYS,
  MONTH_NAME,
  MONTH_INDEX,
  NUMBER_OF_MONTH_IN_ROW,
  NUMBER_OF_YEAR_IN_ROW,
} from 'components/DatePicker/constants';
import s from 'components/DatePicker/calendar/Calendar.css';

const expandMore = (
  <svg width="24" height="24" viewBox="0 0 24 24">
    <path fill="#fff" d="M16.59 8.59L12 13.17 7.41 8.59 6 10l6 6 6-6z" />
    <path d="M0 0h24v24H0z" fill="none" />
  </svg>
);

const expandLess = (
  <svg width="24" height="24" viewBox="0 0 24 24">
    <path fill="#fff" d="M12 8l-6 6 1.41 1.41L12 10.83l4.59 4.58L18 14z" />
    <path d="M0 0h24v24H0z" fill="none" />
  </svg>
);

class Calendar extends Component {
  static propTypes = {
    onClose: func,
    onDateChanged: func,
    dateFormat: string.isRequired,
    date: objectOf(any).isRequired,
    maxDate: objectOf(any).isRequired,
    minDate: objectOf(any).isRequired,
  };

  static defaultProps = {
    onClose: noop,
    onDateChanged: noop,
  };

  constructor(props) {
    super(props);
    const { date, maxDate, minDate } = props;
    const resolveDate = moment(date).isValid() ? date : maxDate;
    this.state = {
      today: moment().set({ hour: 0, minute: 0, second: 0 }),
      maxDate,
      minDate,
      maxYear: maxDate.year(),
      minYear: minDate.year(),
      isClickingYear: false,
      isClickingMonth: false,
      ...this.resolveStateFromDate(resolveDate),
    };
  }

  componentDidMount() {
    const { onDateChanged, dateFormat } = this.props;
    const { current } = this.state;
    onDateChanged(moment(current).format(dateFormat));
  }

  onMonthClick = event => {
    event && event.preventDefault();
    this.setState(oldState => ({
      isClickingMonth: !oldState.isClickingMonth,
      isClickingYear: false,
    }));
  };

  onMonthSelected = month => event => {
    const { selectedYear, current } = this.state;
    this.gotoDate(moment([selectedYear, month, current.date()]));
    event && event.preventDefault();
    this.setState({
      selectedMonth: month,
      isClickingMonth: false,
    });
  };

  onYearClick = event => {
    event && event.preventDefault();
    this.setState(oldState => ({
      isClickingYear: !oldState.isClickingYear,
      isClickingMonth: false,
    }));
  };

  onYearSelected = year => event => {
    event && event.preventDefault();
    this.setState({
      selectedYear: year,
      isClickingYear: false,
    });
  };

  getCalendarDates = () => {
    const { current, selectedMonth, selectedYear } = this.state;
    const displayMonth = selectedMonth + 1 || current.add(1, 'M').month();
    const displayYear = selectedYear || current.year();
    return calendar(displayMonth, displayYear);
  };

  resolveStateFromDate = date => ({
    current: date,
    month: date.month(),
    year: date.year(),
    selectedYear: date.year(),
    selectedMonth: date.month(),
  });

  gotoDate = date => event => {
    event && event.preventDefault();
    const { onDateChanged, dateFormat } = this.props;
    this.setState(this.resolveStateFromDate(date), () =>
      onDateChanged(moment(date).format(dateFormat)),
    );
  };

  parseYearRange = () => {
    const { maxYear, minYear } = this.state;
    const yearRange = Array.from(
      { length: maxYear - minYear + 1 },
      (value, index) => minYear + index,
    );
    return chunk(yearRange, NUMBER_OF_YEAR_IN_ROW);
  };

  renderMonthMatrix = () => {
    const { maxDate, minDate, selectedYear, current } = this.state;
    const quarter = chunk(Object.keys(MONTH_NAME), NUMBER_OF_MONTH_IN_ROW);
    return quarter.map((monthRow, index) => (
      <div key={uniqueId(index)} className={s.monthRow}>
        {monthRow.map(month => {
          const compareMaxDate = moment([
            selectedYear,
            MONTH_INDEX[month],
            maxDate.date(),
          ]).set({
            hour: 0,
            minute: 0,
            second: 0,
            millisecond: 0,
          });
          const compareMinDate = moment([
            selectedYear,
            MONTH_INDEX[month],
            maxDate.date(),
          ]).set({
            hour: 0,
            minute: 0,
            second: 0,
            millisecond: 100,
          });
          const isValidMonth =
            compareMaxDate <= maxDate && compareMinDate >= minDate;
          const currentMonthStyle =
            current.month() === MONTH_INDEX[month] ? s.currentMonthYear : '';

          const className = isValidMonth
            ? `${s.monthCell} ${currentMonthStyle}`
            : `${s.monthCell} ${s.invalidMonth}`;
          const onClick = isValidMonth
            ? this.onMonthSelected(MONTH_INDEX[month])
            : noop;
          const props = {
            className,
            onClick,
          };
          return (
            <div key={month} {...props}>
              <div>{MONTH_NAME[month]}</div>
            </div>
          );
        })}
      </div>
    ));
  };

  renderMonthList = () => {
    const { isClickingMonth } = this.state;
    return isClickingMonth ? (
      <div className={s.monthList}>{this.renderMonthMatrix()}</div>
    ) : null;
  };

  // Render calendar week
  renderWeekDays = () => {
    const { isClickingYear, isClickingMonth } = this.state;
    return (
      !isClickingYear &&
      !isClickingMonth &&
      this.getCalendarDates().map((week, index) => (
        <div className={s.week} key={week[index]}>
          {week.map((day, weekIndex) =>
            this.renderCalendarDate(day, weekIndex),
          )}
        </div>
      ))
    );
  };

  // Render calendar date as returned from the calendar builder function
  renderCalendarDate = (date, index) => {
    const {
      today,
      minDate,
      maxDate,
      selectedMonth,
      selectedYear,
      current,
    } = this.state;
    const dateItem = moment(date.join('-'));
    const sameDate = !dateItem.diff(current);
    const isToday = !dateItem.diff(today);
    const inMonth =
      (selectedMonth || selectedMonth === 0) &&
      selectedYear &&
      dateItem.month() === selectedMonth;
    const shortMaxDate = moment(maxDate).set({
      hour: 23,
      minute: 59,
      second: 59,
    });
    const isValidDate = dateItem < shortMaxDate && dateItem >= minDate;
    const onClick = isValidDate ? this.gotoDate(dateItem) : noop;
    const props = {
      index,
      onClick,
    };
    const formatToday = isToday || sameDate ? `${s.today}` : '';
    const formatMonth = inMonth ? `${s.month}` : '';
    const dateStyles = isValidDate
      ? `${formatMonth} ${formatToday}`
      : `${s.invalidDate}`;
    return (
      <div
        key={dateItem.unix()}
        className={`${s.dateOfMonth} ${dateStyles}`}
        {...props}
      >
        {zeroPad(dateItem.date(), 2)}
      </div>
    );
  };

  // Render the label for day of week
  renderDayLabel = (day, index) => {
    const { isClickingMonth, isClickingYear } = this.state;
    const dayLabel = WEEK_DAYS[day].toUpperCase();

    return !isClickingMonth && !isClickingYear ? (
      <div key={dayLabel} className={s.dayOfWeek} tabIndex={index}>
        {dayLabel}
      </div>
    ) : null;
  };

  renderChevron = value => (value ? expandLess : expandMore);

  // Render month and year header
  renderMonthAndYear = () => {
    const {
      selectedMonth,
      selectedYear,
      isClickingMonth,
      isClickingYear,
    } = this.state;
    const nameOfMonth = MONTH_NAME[Object.keys(MONTH_NAME)[selectedMonth]];

    return (
      <div className={s.calendarHeader}>
        <div className={s.monthAndYear} onClick={this.onMonthClick}>
          {nameOfMonth}
          {this.renderChevron(isClickingMonth)}
        </div>
        <div className={s.monthAndYear} onClick={this.onYearClick}>
          {selectedYear}
          {this.renderChevron(isClickingYear)}
        </div>
      </div>
    );
  };

  renderYearMatrix = () => {
    const { minDate, maxDate } = this.props;
    const { selectedMonth, current } = this.state;
    const yearMatrix = this.parseYearRange();
    return yearMatrix.reverse().map((yearRow, index) => {
      let fullYearRow = null;
      if (yearRow.length < NUMBER_OF_YEAR_IN_ROW) {
        fullYearRow = Array.from(
          { length: NUMBER_OF_YEAR_IN_ROW },
          (_, rowIndex) => rowIndex + yearRow[0],
        );
      } else {
        fullYearRow = yearRow.slice();
      }
      return (
        <div key={uniqueId(index)} className={s.yearRow}>
          {fullYearRow.reverse().map(year => {
            const compareMaxDate = moment([
              year,
              selectedMonth,
              maxDate.date(),
            ]).set({
              hour: 0,
              minute: 0,
              second: 0,
              millisecond: 0,
            });
            const compareMinDate = moment([
              year,
              selectedMonth,
              maxDate.date(),
            ]).set({
              hour: 0,
              minute: 0,
              second: 0,
              millisecond: 100,
            });
            const isValidYear =
              compareMaxDate <= maxDate && compareMinDate >= minDate;
            const currentYearStyle =
              current.year() === year ? s.currentMonthYear : '';
            const className = isValidYear
              ? `${s.yearCell}  ${currentYearStyle}`
              : `${s.yearCell} ${s.invalidYear}`;
            const onClick = isValidYear ? this.onYearSelected(year) : noop;
            const props = {
              className,
              onClick,
            };
            return (
              <li key={year} {...props}>
                {year}
              </li>
            );
          })}
        </div>
      );
    });
  };

  renderYearList = () => {
    const { isClickingYear } = this.state;
    return isClickingYear ? (
      <div className={s.yearList}>{this.renderYearMatrix()}</div>
    ) : null;
  };

  renderOkButton = () => {
    const { isClickingMonth, isClickingYear } = this.state;
    const { onClose } = this.props;
    return !isClickingMonth && !isClickingYear ? (
      <button className={s.buttonOk} onClick={onClose}>
        Đồng ý
      </button>
    ) : null;
  };

  render() {
    const { current } = this.state;
    return (
      <>
        <div className={s.calendar}>
          <div className={s.title}>
            <div className={s.day}>{WEEK_DATES[current.day()]}</div>
            <div className={s.fullDate}>
              Ngày {zeroPad(current.date(), 2)}{' '}
              {MONTH_NAME[Object.keys(MONTH_NAME)[current.month()]]} Năm{' '}
              {current.year()}
            </div>
          </div>
          {this.renderMonthAndYear()}
          <div className={s.grid}>
            <div className={s.daysOfWeek}>
              {Object.keys(WEEK_DAYS).map(this.renderDayLabel)}
            </div>
            <div className={s.datesOfMonth}>{this.renderWeekDays()}</div>
            {this.renderYearList()}
            {this.renderMonthList()}
          </div>
          {this.renderOkButton()}
        </div>
      </>
    );
  }
}

export default withStyles(s)(Calendar);

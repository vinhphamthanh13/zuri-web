/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

/* eslint-disable jsx-a11y/label-has-for, css-modules/no-unused-class, jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions */

import React, { Component } from 'react';
import { func, bool, number } from 'prop-types';
import { noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { OTP_COUNT_DOWN_TEXT, MAX_RETRY } from './constants';
import s from './style.css';

const zeroPad = (value, length) => `${value}`.padStart(length, '0');

class OTPCountDown extends Component {
  static propTypes = {
    setOTPTimeOut: func,
    resendOTP: func,
    disabled: bool,
    retry: number,
  };

  static defaultProps = {
    resendOTP: noop,
    setOTPTimeOut: noop,
    disabled: false,
    retry: MAX_RETRY,
  };

  constructor(props) {
    super(props);
    this.state = {
      seconds: null,
      start: null,
      stop: false,
      retryCount: 1,
    };
    this.countDownId = null;
  }

  static getDerivedStateFromProps(props, state) {
    const { seconds, start } = props;
    const { seconds: cachedSeconds, start: cachedStart } = state;
    if (seconds !== cachedSeconds || start !== cachedStart) {
      const resolvedSecond =
        !Object.is(cachedSeconds, null) && cachedSeconds >= 0
          ? cachedSeconds
          : seconds;
      return {
        start,
        stop: cachedSeconds === 0,
        seconds: resolvedSecond,
      };
    }
    return null;
  }

  componentDidMount() {
    this.startTimer();
  }

  componentDidUpdate(prevProps, prevState) {
    const { start, stop } = prevState;
    const { start: cachedStart, stop: cachedStop, retryCount } = this.state;
    const { setOTPTimeOut, disabled, retry } = this.props;

    // Comparing new states with old ones to determine the timer is ended
    // and notifying the timeout
    if (cachedStop && cachedStop !== stop) {
      setOTPTimeOut();
      this.stopTimer();
    }

    // Determine whether the timer should restart or not based on the props
    if (cachedStart && cachedStart !== start) {
      !disabled && retryCount <= retry && this.startTimer();
    }
  }

  componentWillUnmount() {
    this.stopTimer();
  }

  startTimer = () => {
    this.countDownId = setInterval(() => {
      const { seconds } = this.state;
      this.setState({
        seconds: seconds - 1 >= 0 ? seconds - 1 : 0,
      });
    }, 1000);
  };

  stopTimer = () => {
    clearInterval(this.countDownId);
  };

  handleResendOTP = () => {
    const { resendOTP, retry } = this.props;
    const { retryCount } = this.state;
    if (retryCount < retry) {
      this.setState(oldState => ({
        seconds: null,
        retryCount: oldState.retryCount + 1,
      }));
    }
    resendOTP(!(retryCount < retry));
  };

  renderCountDown = timer => {
    const { disabled } = this.props;
    const second = timer % 60;
    const minute = Math.floor(timer / 60);
    return disabled ? (
      <span>{OTP_COUNT_DOWN_TEXT.RESET}</span>
    ) : (
      <span>{`${zeroPad(minute, 2)}:${zeroPad(second, 2)}`}</span>
    );
  };

  render() {
    const { disabled } = this.props;
    const { seconds } = this.state;
    return (
      <div className={s.OTPCountDown}>
        <div className={s.timerLabel}>{OTP_COUNT_DOWN_TEXT.EXPIRED}</div>
        <div className={s.timer}>{this.renderCountDown(seconds)}</div>
        {(seconds === 0 || disabled) && (
          <div className={s.resendCommand} onClick={this.handleResendOTP}>
            {OTP_COUNT_DOWN_TEXT.RESEND}
          </div>
        )}
      </div>
    );
  }
}

export default withStyles(s)(OTPCountDown);

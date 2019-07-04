import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Button } from 'homecredit-ui';
import classnames from 'classnames';
import { sendGAEvent } from 'utils/ga';

import s from './CallButton.css';
import iconCall from './images/call.svg';

class CallButton extends Component {
  state = {
    isShowingHotLine: false,
  };

  handleShowModal = () => {
    this.setState({ isShowingHotLine: true });
    this.trackingCallButton();
  };

  handleCloseModal = () => {
    this.setState({ isShowingHotLine: false });
  };

  trackingCallButton = () => {
    sendGAEvent('Click_call_hotline', 'Hotline', 'Hotline');
  };

  render() {
    const { className } = this.props;
    const { isShowingHotLine } = this.state;

    return (
      <>
        <a
          href="tel:*6868"
          className={classnames(s.welcomeXs, className)}
          onClick={this.trackingCallButton}
        >
          <div className={s.phone}>
            <img src={iconCall} alt="call button" />
          </div>
        </a>
        <div
          className={classnames(s.welcome, className)}
          onClick={this.handleShowModal}
        >
          <div className={s.phone}>
            <img src={iconCall} alt="call button" />
          </div>
        </div>

        {isShowingHotLine && (
          <div className={s.backdrop}>
            <div className={s.modalContent}>
              <p>
                Hãy gọi <span className={s.hotNumber}>*6868</span> hoặc{' '}
                <span className={s.hotNumber}>(028) 38 999 666</span> để được
                hướng dẫn thêm!
              </p>
              <Button
                stretch
                className={s.closeModalButton}
                onClick={this.handleCloseModal}
              >
                Đóng
              </Button>
            </div>
          </div>
        )}
      </>
    );
  }
}

CallButton.propTypes = {
  className: PropTypes.string,
};

CallButton.defaultProps = {
  className: '',
};

export default withStyles(s)(CallButton);

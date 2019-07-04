import React, { Component } from 'react';
import { string, bool, func } from 'prop-types';
import classNames from 'classnames';
import { IconButton, Button } from 'homecredit-ui';
import noop from 'lodash/noop';

import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './HeaderBar.css';

const BOCVNIcon = () => (
  <svg fill="#fff" viewBox="0 -40 153 153">
    <g>
      <path d="M76.503 0C34.255 0 0 32.96 0 73.637c0 14.343 4.261 27.734 11.634 39.052H48.53c-11.201-8.566-18.477-22.111-18.477-37.405 0-25.918 20.793-46.923 46.443-46.923s46.444 21.005 46.444 46.923c0 15.294-7.266 28.84-18.477 37.405h36.902C148.744 101.37 153 87.98 153 73.637 153 32.96 118.76 0 76.503 0" />
      <path d="M50.39 87.036c3.548 11.73 13.276 20.156 24.73 20.156 11.465 0 21.203-8.425 24.742-20.156H50.39" />
    </g>
  </svg>
);

class HeaderBar extends Component {
  static propTypes = {
    title: string,
    hideBackButton: bool,
    hideNextButton: bool,
    onBackClick: func,
    onNextClick: func,
    nextText: string,
    disabledBackButton: bool,
    disabledNextButton: bool,
    className: string,
  };

  static defaultProps = {
    title: 'Hello http://www.bocvietnam.com',
    hideBackButton: false,
    hideNextButton: false,
    onBackClick: noop,
    onNextClick: noop,
    nextText: 'Tiếp tục',
    disabledBackButton: false,
    disabledNextButton: false,
    className: '',
  };

  render() {
    const {
      title,
      hideBackButton,
      hideNextButton,
      nextText,
      onBackClick,
      onNextClick,
      disabledBackButton,
      disabledNextButton,
      className,
    } = this.props;
    const nextTextClass = classNames(s.nextText, {
      [s.disableText]: disabledNextButton,
    });

    return (
      <div className={className}>
        <header className={s.header}>
          <span>
            {!hideBackButton && (
              <IconButton
                smallIcon
                icon="arrow_back"
                onClick={onBackClick}
                disabled={disabledBackButton}
              />
            )}
          </span>
          <span>
            <IconButton icon={<BOCVNIcon />} />
          </span>
          <span>
            {!hideNextButton && (
              <Button
                onClick={onNextClick}
                disabled={disabledNextButton}
                className={s.btnNext}
              >
                <span className={nextTextClass}>{nextText}</span>
              </Button>
            )}
          </span>
        </header>
        {title && <h1 className={s.title}>{title}</h1>}
      </div>
    );
  }
}

export default withStyles(s)(HeaderBar);

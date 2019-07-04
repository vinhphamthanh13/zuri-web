import React, { Component } from 'react';
import { string, bool, func } from 'prop-types';
import classNames from 'classnames';
import { IconButton, Button } from 'homecredit-ui';
import noop from 'lodash/noop';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Logo from '../Logo';
import UserHeader from './UserHeader';
import s from './HeaderBar.css';

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
    isCheckAuth: bool,
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
    isCheckAuth: false,
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
      isCheckAuth,
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
            <IconButton icon={<Logo />} />
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
            <UserHeader isCheckAuth={isCheckAuth} />
          </span>
        </header>
        {title && <h1 className={s.title}>{title}</h1>}
      </div>
    );
  }
}

export default withStyles(s)(HeaderBar);

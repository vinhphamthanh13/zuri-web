import React from 'react';
import { func, bool } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Button } from 'homecredit-ui';
import { NEXT_TEXT } from './constant';
import s from './NextButton.css';

const NextButton = props => {
  const { hideNextButton, onNextClick, disabledNextButton, mobile } = props;
  const nextStyle = mobile
    ? `${s.mobileView} ${s.wrapper}`
    : `${s.desktopView} ${s.wrapper}`;
  return !hideNextButton ? (
    <div className={nextStyle}>
      <Button
        onClick={onNextClick}
        disabled={disabledNextButton}
        className={s.nextButton}
      >
        <span>{NEXT_TEXT}</span>
      </Button>
    </div>
  ) : null;
};

NextButton.propTypes = {
  onNextClick: func.isRequired,
  hideNextButton: bool,
  disabledNextButton: bool,
  mobile: bool,
};

NextButton.defaultProps = {
  disabledNextButton: false,
  hideNextButton: false,
  mobile: false,
};

export default withStyles(s)(NextButton);

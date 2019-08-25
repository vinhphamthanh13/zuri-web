import React from 'react';
import { string, func, bool } from 'prop-types';
import { noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Clear, CheckCircleOutline, ErrorOutline } from 'constants/svg';
import s from './Modal.css';

const Modal = props => {
  const { errorIcon, successIcon, title, message, callback } = props;
  return (
    <div className={s.overlay}>
      <div className={s.modal}>
        <div className={s.title}>
          {errorIcon && <ErrorOutline />}
          {successIcon && <CheckCircleOutline />}
          <div>{title}</div>
          <div className={s.cta} onClick={callback}>
            <Clear />
          </div>
        </div>
        <div className={s.content}>{message}</div>
      </div>
    </div>
  );
};

Modal.propTypes = {
  errorIcon: bool,
  successIcon: bool,
  title: string,
  message: string,
  callback: func,
};

Modal.defaultProps = {
  errorIcon: false,
  successIcon: false,
  title: 'title',
  message: 'some meessage',
  callback: noop,
};

export default withStyles(s)(Modal);

import React from 'react';
import { string, func, bool } from 'prop-types';
import { noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Button from 'components/Button';
import { OK } from 'constants/common';
import { Clear, CheckCircleOutline, ErrorOutline } from 'constants/svg';
import s from './Modal.css';

const Modal = props => {
  const { errorIcon, successIcon, title, message, onClose, onAgree } = props;
  const titleBackground = successIcon
    ? `${s.title} ${s.titleSuccess}`
    : s.title;
  return (
    <div className={s.overlay}>
      <div className={s.modal}>
        <div className={titleBackground}>
          {errorIcon && <ErrorOutline />}
          {successIcon && <CheckCircleOutline />}
          <div>{title}</div>
          <div className={s.close} onClick={onClose}>
            <Clear />
          </div>
        </div>
        <div className={s.content}>
          <div className={s.message}>{message}</div>
          <div className={s.ok}>
            <Button variant="outlined" label={OK} onClick={onAgree} />
          </div>
        </div>
      </div>
    </div>
  );
};

Modal.propTypes = {
  errorIcon: bool,
  successIcon: bool,
  title: string,
  message: string,
  onClose: func,
  onAgree: func,
};

Modal.defaultProps = {
  errorIcon: false,
  successIcon: false,
  title: 'title',
  message: 'message',
  onClose: noop,
  onAgree: noop,
};

export default withStyles(s)(Modal);

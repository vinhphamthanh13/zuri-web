import React from 'react';
import { string, func } from 'prop-types';
import { noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Clear } from 'constants/svg';
import s from './Modal.css';

const Modal = props => {
  const { title, message, callback } = props;

  return (
    <div className={s.overlay}>
      <div className={s.modal}>
        <div className={s.title}>
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
  title: string,
  message: string,
  callback: func,
};

Modal.defaultProps = {
  title: 'title',
  message: 'some meessage',
  callback: noop,
};

export default withStyles(s)(Modal);

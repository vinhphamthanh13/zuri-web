import React, { PureComponent } from 'react';
import { compose } from 'redux';
import { connect } from 'react-redux';
import { Modal } from 'homecredit-ui';
import { func, noop, string } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import { clearAlert } from 'actions/saRegister';
import { ALERT_TEXT } from './constant';
import s from './Alert.css';

class Alert extends PureComponent {
  static propTypes = {
    clearMessage: func,
    title: string,
    message: string,
  };

  static defaultProps = {
    clearMessage: noop,
    title: '',
    message: '',
  };

  onCloseModal = () => {
    const { clearMessage } = this.props;
    clearMessage();
  };

  render() {
    const { title, message } = this.props;
    const isDisplay = !!message;
    return (
      <div>
        <Modal
          isOpen={isDisplay}
          onClose={this.onCloseModal}
          footer={
            <div className={s.modalFooter} onClick={this.onCloseModal}>
              <h4>{ALERT_TEXT.argee}</h4>
            </div>
          }
        >
          <div className={s.modalBody}>
            {title && <h3 className={s.modalTitle}>{title}</h3>}
            <p>{message}</p>
          </div>
        </Modal>
      </div>
    );
  }
}

export const mapDispatchToProps = dispatch => ({
  clearMessage() {
    dispatch(clearAlert());
  },
});

function mapStateToProps({ saRegister }) {
  const { title, message } = saRegister.alert;
  return { title, message };
}

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withStyles(s),
);

export default enhance(Alert);

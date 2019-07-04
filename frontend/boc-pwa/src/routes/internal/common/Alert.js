import React, { PureComponent } from 'react';
import { compose } from 'redux';
import { connect } from 'react-redux';
import { Modal } from 'homecredit-ui';
import { func, noop, any, objectOf } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import { alerts } from 'actions/internal';
import { ALERT_TEXT } from './constant';
import s from './Alert.css';

class Alert extends PureComponent {
  static propTypes = {
    clearMessage: func,
    alert: objectOf(any),
  };

  static defaultProps = {
    clearMessage: noop,
    alert: {},
  };

  onCloseModal = () => {
    const { clearMessage } = this.props;
    clearMessage();
  };

  render() {
    const { alert } = this.props;
    const { type, message } = alert;
    const isDisplay = !!type;
    const title = type ? ALERT_TEXT[type] : '';
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
            <h3 className={s.modalTitle}>{title}</h3>
            <p>{message}</p>
          </div>
        </Modal>
      </div>
    );
  }
}

export const mapDispatchToProps = dispatch => ({
  clearMessage() {
    dispatch(alerts.clear());
  },
});

function mapStateToProps({ internal }) {
  const { alert } = internal;
  return { alert };
}

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withStyles(s),
);

export default enhance(Alert);

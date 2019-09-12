import React from 'react';
import { bool, string, func } from 'prop-types';
import { connect } from 'react-redux';
import Modal from 'components/Modal';
import { findValueByKey } from 'utils/reduceStateProperty';
import { resetSuccess } from 'actions/commonActions';

const Success = props => {
  const { isSuccess, successMessage, resetSuccess: resetSuccessAction } = props;

  return isSuccess ? (
    <Modal
      title="Thông báo"
      message={successMessage}
      callback={resetSuccessAction}
      successIcon
    />
  ) : null;
};

Success.propTypes = {
  isSuccess: bool,
  successMessage: string.isRequired,
  resetSuccess: func.isRequired,
};

Success.defaultProps = {
  isSuccess: false,
};

const mapStateToProps = state => {
  const isSuccess = [];
  const successMessage = [];
  findValueByKey(state, 'isSuccess', isSuccess);
  findValueByKey(state, 'successMessage', successMessage);
  return {
    isSuccess: isSuccess.reduce((final, current) => final || current, false),
    successMessage: successMessage.reduce(
      (final, current) => final || current,
      '',
    ),
  };
};

const mapDispatchToProps = dispatch => ({
  resetSuccess: () => dispatch(resetSuccess()),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(Success);

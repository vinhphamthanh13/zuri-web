import React from 'react';
import { bool, string, func } from 'prop-types';
import { connect } from 'react-redux';
import Modal from 'components/Modal';
import { findValueByKey } from 'utils/reduceStateProperty';
import { resetError } from 'actions/commonActions';

const Error = props => {
  const { isError, errorMessage, resetError: resetErrorAction } = props;

  return isError ? (
    <Modal
      title="Thông báo"
      message={errorMessage}
      callback={resetErrorAction}
      errorIcon
    />
  ) : null;
};

Error.propTypes = {
  isError: bool,
  errorMessage: string.isRequired,
  resetError: func.isRequired,
};

Error.defaultProps = {
  isError: false,
};

const mapStateToProps = state => {
  const isError = [];
  const errorMessage = [];
  findValueByKey(state, 'isError', isError);
  findValueByKey(state, 'errorMessage', errorMessage);
  return {
    isError: isError.reduce((final, current) => final || current, false),
    errorMessage: errorMessage.reduce((final, current) => final || current, ''),
  };
};

const mapDispatchToProps = dispatch => ({
  resetError: () => dispatch(resetError()),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(Error);

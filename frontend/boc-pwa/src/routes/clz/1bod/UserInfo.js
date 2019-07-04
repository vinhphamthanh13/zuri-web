/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
/* eslint-disable jsx-a11y/anchor-is-valid, jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions */

import React, { Component } from 'react';
import { compose } from 'redux';
import {
  objectOf,
  any,
  func,
  object,
  bool,
  oneOfType,
  number,
} from 'prop-types';
import { connect } from 'react-redux';
import { withFormik } from 'formik';
import { pick, isEmpty, noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import Modal from 'components/common/Modal';
import { getValidationSchema } from 'utils/validations';
import { sendGAEvent, sendGATimming } from 'utils/ga';
import { imageCompression } from 'utils/image';
import { FORM_STATUS } from 'constants/common';
import classnames from 'classnames';

import { OTP_TEXTS, USER_INFO_TEXTS, USER_FORM_FIELDS } from './constant';
import { mapStateToProps, mapDispatchToProps } from './commonProps';
import s from './UserInfo.css';

class UserInfo extends Component {
  static propTypes = {
    formStatus: number,
    errors: objectOf(any),
    values: objectOf(any),
    setFieldValue: func,
    setFieldTouched: func.isRequired,
    onChange1BODInfo: func,
    updateFormStatus: func,
    validateForm: func,
    handleBlur: func,
    goToNextStep: func,
    touched: oneOfType([bool, object]),
    setFieldError: func,
  };

  static defaultProps = {
    formStatus: FORM_STATUS.INITIAL,
    errors: {},
    values: {},
    setFieldValue: noop,
    onChange1BODInfo: noop,
    handleBlur: noop,
    updateFormStatus: noop,
    validateForm: noop,
    goToNextStep: noop,
    touched: {},
    setFieldError: noop,
  };

  constructor(props) {
    super(props);
    this.state = {
      isOpenModal: false,
      modalContent: null,
    };

    // Save time begin for GA
    this.startTime = Date.now();
  }

  /**
   * Update Form Status to initial after render
   *
   * @return {void}
   */
  componentDidMount() {
    this.props.updateFormStatus(FORM_STATUS.INITIAL);

    sendGAEvent('Visit_personal_info', 'Personal_info', 'Personal Info');
  }

  /**
   * Validate when form status is changed
   *
   * @return {void}
   */
  async componentWillReceiveProps(nextProps) {
    const {
      updateFormStatus,
      formStatus,
      validateForm,
      goToNextStep,
      setFieldTouched,
    } = this.props;
    const { formStatus: nextStatus } = nextProps;

    if (formStatus === FORM_STATUS.INITIAL && formStatus !== nextStatus) {
      sendGAEvent('Submit_personal_info', 'Personal_info', 'Personal Info');

      const errors = await validateForm();
      if (isEmpty(errors)) {
        goToNextStep();
      } else {
        updateFormStatus(FORM_STATUS.INITIAL);
        Object.keys(errors).forEach(error => setFieldTouched(error));
      }
    }
  }

  /**
   * Tracking ga timing
   */
  componentWillUnmount() {
    // Send time begin for GA
    const timeFill = Date.now() - this.startTime;

    sendGATimming('1BOD - session1', timeFill, 'Personal Info');
  }

  /**
   * Update state when modal is opened
   */
  onOpenModal = () => {
    this.setState({
      isOpenModal: true,
    });
  };

  /**
   * Update state when modal is closed
   */
  onCloseModal = () => {
    this.setState({
      isOpenModal: false,
    });
  };

  /**
   * Handle help icon on click
   * Replace modal body content state
   */
  handleHelpIconClick = modalContent => {
    const { trackingAction, trackingLabel } = modalContent;

    this.setState({
      modalContent,
    });
    sendGAEvent(trackingAction, trackingLabel, 'Personal Info');
  };

  /**
   * Update Form Field Value & Redux State
   *
   * @param  {String|Object} data  Field Name
   * @param  {String} info Field Value
   *
   * @return {Void}
   */
  handleFieldChange = (data, info) => {
    const { name, value } = data.target || { name: data, value: info };
    const { setFieldValue, onChange1BODInfo, setFieldTouched } = this.props;

    if (typeof value === 'string' && !value.trim() && value.length) return;

    // Update Form Value
    setFieldValue(name, value);

    // Update Redux state
    onChange1BODInfo(name, value);
    setFieldTouched(name, true);
  };

  renderComponent = item => {
    const {
      values,
      errors,
      touched,
      setFieldTouched,
      setFieldError,
    } = this.props;
    const {
      name,
      label,
      component,
      modalContent,
      trackingLabel,
      trackingAction,
      className,
      ...rest
    } = item;

    const handleBlur = e => {
      sendGAEvent(trackingAction, trackingLabel, 'Personal Info');

      this.props.handleBlur(e);
    };

    const handleFieldChange = async (data, info) => {
      const newData = data;
      if (name === 'dayOfBirth' || name === 'gender' || name === 'userImage') {
        sendGAEvent(trackingAction, trackingLabel, 'Personal Info');
      }

      // Compress Image
      if (name === 'userImage') {
        const images = data.target.value;
        const imageKey = Object.keys(images)[0];
        if (!imageKey) {
          newData.target.value = {};
        } else {
          newData.target.value = {
            [imageKey]: await imageCompression(images[imageKey]),
          };
        }
      }

      this.handleFieldChange(newData, info);
    };

    return (
      <div className={classnames(className, s.wrapperInputField)} key={name}>
        <item.component
          label={label}
          name={name}
          value={values[name]}
          onChange={handleFieldChange}
          onBlur={handleBlur}
          touched={!!touched[name]}
          errorMsg={errors[name] || ''}
          onClickHelpIcon={() => {
            this.handleHelpIconClick(modalContent);
            this.onOpenModal();
          }}
          setFieldTouched={setFieldTouched}
          setFieldError={setFieldError}
          className={s.inputField}
          {...rest}
        />
      </div>
    );
  };

  render() {
    const { isOpenModal, modalContent } = this.state;

    return (
      <div className="container">
        <div className="row">
          <p className={classnames('col-12', s.title)}>{`${
            USER_INFO_TEXTS.NOTE
          }`}</p>
          {USER_FORM_FIELDS.map(item => this.renderComponent(item))}
        </div>

        <Modal
          onOpen={this.onOpenModal}
          onClose={this.onCloseModal}
          isOpen={isOpenModal}
          footer={
            <div className={s.modalFooter} onClick={this.onCloseModal}>
              <h4>{OTP_TEXTS.AGREE}</h4>
            </div>
          }
        >
          <div className={s.modalBody}>
            {modalContent && (
              <React.Fragment>
                <h3>{modalContent.title}</h3>
                {modalContent.image && (
                  <img src={modalContent.image} alt="huongdan" />
                )}
                {modalContent.contents.map(content => (
                  <p key={content}>{content}</p>
                ))}
              </React.Fragment>
            )}
          </div>
        </Modal>
      </div>
    );
  }
}

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withFormik({
    validateOnBlur: false,
    mapPropsToValues: props =>
      pick(props.firstBODInfo, [
        'firstName',
        'middleName',
        'lastName',
        'dayOfBirth',
        'userImage',
        'gender',
      ]),
    validationSchema: getValidationSchema([
      'firstName',
      'middleName',
      'lastName',
      'dayOfBirth',
      'userImage',
      'gender',
    ]),
  }),
  withStyles(s),
);

export default enhance(UserInfo);

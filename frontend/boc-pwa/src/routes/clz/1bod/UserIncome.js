/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { compose } from 'redux';
import { connect } from 'react-redux';
import { withFormik } from 'formik';
import { pick, isEmpty, noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Input, NumberInput } from 'homecredit-ui';
import Modal from 'components/common/Modal';
import { getValidationSchema } from 'utils/validations';
import { FORM_STATUS } from 'constants/common';
import { sendGAEvent } from 'utils/ga';
import classnames from 'classnames';

import { POPUP_TYPE, USER_INCOME_FIELDS, REGEX } from './constant';
import { mapStateToProps, mapDispatchToProps } from './commonProps';
import FamilyBook from './FamilyBook';
import s from './UserIncome.css';

const BOD_FIELD_NAME = {
  SOCIAL_INFO: 'monthlyIncome',
  MONTHLY_PAY: 'monthlyPayment',
  FBR: 'familyBook',
  DRR: 'drivingLicence',
  ID_TYPE: 'identificationType',
  ID_CARD: 'idCard',
};

const FIRST_BOD_FIELD_NAMES = [
  BOD_FIELD_NAME.SOCIAL_INFO,
  BOD_FIELD_NAME.MONTHLY_PAY,
  BOD_FIELD_NAME.FBR,
  BOD_FIELD_NAME.DRR,
  BOD_FIELD_NAME.ID_TYPE,
  BOD_FIELD_NAME.ID_CARD,
];

class UserIncome extends Component {
  static propTypes = {
    errors: PropTypes.objectOf(PropTypes.any),
    values: PropTypes.objectOf(PropTypes.any),
    setFieldValue: PropTypes.func,
    onChange1BODInfo: PropTypes.func,
    handleBlur: PropTypes.func,
    validateForm: PropTypes.func.isRequired,
    setFieldTouched: PropTypes.func.isRequired,
    touched: PropTypes.oneOfType([PropTypes.bool, PropTypes.object]),
  };

  static defaultProps = {
    errors: {},
    values: {},
    setFieldValue: noop,
    onChange1BODInfo: noop,
    handleBlur: noop,
    touched: false,
  };

  constructor(props) {
    super(props);

    this.state = {
      isOpenModal: false,
    };

    // Save time begin for GA
    this.startTime = Date.now();
    this.blurDL = this.handleBlur.bind(
      this,
      'Input_driver_license',
      'Driver_license',
    );
    this.blurFRB = this.handleBlur.bind(this, 'Input_FRB', 'FRB');
  }

  /**
   * Update Form Status to initial after render
   *
   * @return {void}
   */
  componentDidMount() {
    this.props.updateFormStatus(FORM_STATUS.INITIAL);

    sendGAEvent('Visit_income_info', 'Income_info', 'Income Info');
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
      sendGAEvent('Submit_income_info', 'Income_info', 'Income Info');

      const errors = await validateForm();
      if (isEmpty(errors)) {
        goToNextStep();
      } else {
        if (Object.keys(errors).length === 1 && errors.identificationType) {
          this.onOpenModal();
        }

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
    window.ga('send', 'timing', {
      timingCategory: 'Income Info',
      timingVar: '1BOD - session2',
      timingValue: timeFill,
    });
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

  getValidationInput = item => {
    const { values, errors, touched } = this.props;
    const {
      name,
      label,
      trackingLabel,
      trackingAction,
      className,
      ...rest
    } = item;

    const handleBlur = e => {
      sendGAEvent(trackingAction, trackingLabel, 'Income Info');

      this.props.handleBlur(e);
    };

    return (
      <div className={className} key={name}>
        <item.component
          id={name}
          label={label}
          name={name}
          value={values[name]}
          onChange={this.handleFieldChange}
          onBlur={handleBlur}
          touched={touched[name]}
          errorMsg={errors[name]}
          {...rest}
        />
      </div>
    );
  };

  /**
   * Update Form Field Value & Redux State
   *
   * @param  {String} name  Field Name
   * @param  {String} value Field Value
   *
   * @return {Void}
   */
  handleFieldChange = (data, info) => {
    const { name, value } = data.target || { name: data, value: info };
    const { setFieldValue, onChange1BODInfo } = this.props;

    // Prevent special character for idCard
    if (name === 'idCard' && /[^a-zA-Z0-9]/.test(value)) {
      return;
    }

    // Prevent User inputs space
    if (
      (!REGEX.NONE_SPACE.test(value) && value.length) ||
      (name === BOD_FIELD_NAME.DRR && value.length > 12)
    )
      return;
    // Update Form Value
    setFieldValue(name, value);

    // Update Redux state
    onChange1BODInfo(name, value);
  };

  handleChangeIdentificationType = value => {
    const eventLabel = value === 'drivingLicence' ? 'Driver_license' : 'FRB';
    const eventAction =
      value === 'drivingLicence' ? 'Select_driver_license' : 'Select_FRB';

    this.props.setFieldValue('identificationType', value);
    this.props.onChange1BODInfo('identificationType', value);

    sendGAEvent(eventAction, eventLabel, 'Income Info');
  };

  handleBlur = (eventAction, eventLabel, e) => {
    this.props.handleBlur(e);

    sendGAEvent(eventAction, eventLabel, 'Income Info');
  };

  render() {
    const { values, errors, touched } = this.props;

    return (
      <div className="container">
        <div className="row">
          <p className={classnames(s.title, 'col-12')}>
            (*) Thông tin bắt buộc
          </p>
          {USER_INCOME_FIELDS.map(item => this.getValidationInput(item))}

          <div className={classnames(s.familyBookRadio, 'col-12')}>
            <div className={s.radioGroup}>
              <FamilyBook
                type={POPUP_TYPE.DL}
                value="drivingLicence"
                onChange={this.handleChangeIdentificationType}
                groupValue={values.identificationType}
              />
              <FamilyBook
                type={POPUP_TYPE.FRB}
                value="familyBook"
                onChange={this.handleChangeIdentificationType}
                groupValue={values.identificationType}
              />
            </div>
          </div>
          <div className={classnames(s.familyInputWrapper, 'col-12')}>
            {values.identificationType === 'drivingLicence' && (
              <Input
                type="text"
                label="Số Giấy phép lái xe"
                name="drivingLicence"
                value={values.drivingLicence}
                onChange={this.handleFieldChange}
                onBlur={this.blurDL}
                touched={touched.drivingLicence}
                errorMsg={errors.drivingLicence}
                className={s.familyInput}
                require
                msgPosition="below"
              />
            )}
            {values.identificationType === 'familyBook' && (
              <NumberInput
                id="familyBook"
                type="tel"
                label="Số Sổ hộ khẩu"
                name="familyBook"
                decimalSeperator={false}
                value={values.familyBook}
                onChange={this.handleFieldChange}
                onBlur={this.blurFRB}
                touched={touched.familyBook}
                errorMsg={errors.familyBook}
                maxLength="20"
                className={s.familyInput}
                require
                msgPosition="below"
              />
            )}
            <Modal
              onOpen={this.onOpenModal}
              onClose={this.onCloseModal}
              isOpen={this.state.isOpenModal}
              footer={
                <div className={s.modalFooter} onClick={this.onCloseModal}>
                  <h4>Tôi đã hiểu</h4>
                </div>
              }
            >
              <div className={s.modalBody}>
                <h3 className={s.modalTitle}>Lưu ý</h3>

                <p>
                  Vui lòng chọn <b>Giấy phép lái xe</b> hoặc <b>Sổ hộ khẩu</b>
                </p>
              </div>
            </Modal>
          </div>
        </div>
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
    mapPropsToValues: props => pick(props.firstBODInfo, FIRST_BOD_FIELD_NAMES),
    validationSchema: getValidationSchema([...FIRST_BOD_FIELD_NAMES]),
  }),
  withStyles(s),
);

export default enhance(UserIncome);

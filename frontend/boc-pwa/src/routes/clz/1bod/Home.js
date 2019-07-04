/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React, { Component } from 'react';
import { func, number, shape, string, objectOf, any, bool } from 'prop-types';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { get, noop, isString } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import HeaderBar from 'components/Header/HeaderBar';
import { Modal } from 'homecredit-ui';

import {
  setFormStatus,
  updateStep,
  set1BODInfo,
  trackingInfo,
} from 'actions/clz';
import { verifyShortId } from 'actions/shortURL';

import { FORM_STATUS, MESSAGES } from 'constants/common';
import { convertDate } from 'utils/time';
import classnames from 'classnames';
import FullLoading from 'components/common/FullLoading/FullLoading';
import Cycle from 'components/common/FullLoading/Cycle';

import {
  HEADER_TITLES,
  STEPS,
  STEP_NAME,
  STEP_COMPONENT,
  SUBMIT_APPLICATION_FAIL,
  INVALID_SA_CODE,
  AGREE,
  UNIFIED_ID_TYPE,
} from './constant';
import history from '../../../history';
import CallButton from './CallButton';

import s from './Home.css';
import DesktopDesign from './DesktopDesign';
import NextButton from './NextButton';

const FullLoadingCycle = FullLoading(Cycle);

class FirstBOD extends Component {
  static propTypes = {
    params: shape({
      sa: string,
    }),
    updateFormStatus: func,
    updateStep: func,
    onChange1BODInfo: func,
    step: number,
    trackingToken: string,
    firstBODInfo: objectOf(any),
    loaderStatus: bool,
  };

  static defaultProps = {
    params: {
      sa: null,
    },
    updateFormStatus: noop,
    updateStep: noop,
    onChange1BODInfo: noop,
    step: STEPS.WELCOME_PAGE,
    trackingToken: null,
    firstBODInfo: {},
    loaderStatus: false,
  };

  constructor(props) {
    super(props);
    this.state = {
      isOpenModal: false,
      modalId: '',
    };
  }

  async componentDidMount() {
    const { step: componentStep } = this.props;
    // Save time begin for GA
    this.startTime = Date.now();

    // Parse SA Code
    const {
      params: { step, sa },
      onChange1BODInfo,
    } = this.props;

    let partner;
    let tipper;

    if (step && step !== STEP_NAME[componentStep]) {
      partner = step;
      tipper = sa;
    }

    if (partner || sa) {
      try {
        const verify = await verifyShortId(partner || sa);
        const { partnerCode, saCode } = verify;

        if (partnerCode) {
          onChange1BODInfo('partnerCode', partnerCode);
          onChange1BODInfo('tipperCode', tipper);
          onChange1BODInfo('retailAgent', saCode);
          onChange1BODInfo('sa', null);
        } else if (saCode) {
          onChange1BODInfo('retailAgent', saCode);
          onChange1BODInfo('sa', sa);
          onChange1BODInfo('partnerCode', null);
          onChange1BODInfo('tipperCode', null);
        }

        if (verify.errors.length) {
          history.push(`/${STEP_NAME[STEPS.WELCOME_PAGE]}`);
        }
      } catch (error) {
        history.push(`/${STEP_NAME[STEPS.WELCOME_PAGE]}`);
      }
    }

    if (!step && !sa) {
      history.push(`/${STEP_NAME[STEPS.WELCOME_PAGE]}`);
    }
  }

  /**
   * Reset 1bod step
   *
   * @return {void}
   */
  componentWillUnmount() {
    this.props.updateStep(STEPS.WELCOME_PAGE);

    // Send time begin for GA
    const timeFill = Date.now() - this.startTime;
    window.ga('send', 'timing', {
      timingCategory: 'Application process',
      timingVar: '1BOD application',
      timingValue: timeFill,
    });
  }

  /**
   * Go back to the previous step
   *
   * @return {void}
   */
  onClickBack = () => {
    const { step = STEPS.CALCULATE_CASH } = this.props;
    let previousStep = step - 1;
    // Do not allow to back to step 3
    previousStep === 3 && (previousStep -= 1);
    this.props.updateStep(previousStep);
    history.push(`/${STEP_NAME[previousStep]}`);
  };

  /**
   * Set status to form is need validate
   *
   * @return {void}
   */
  onClickNext = () => {
    this.props.updateFormStatus(FORM_STATUS.NEED_VALIDATE);
  };

  /**
   * Update state when modal is opened
   */
  onOpenModal = (modalId = '') => {
    this.setState({
      isOpenModal: true,
      modalId,
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
   * Go to next step, use in children component
   *
   * @return {void}
   */
  goToNextStep = () => {
    const { step, trackingToken, firstBODInfo } = this.props;
    const nextStep = step + 1;
    this.props.updateStep(nextStep);
    history.push(`/${STEP_NAME[nextStep]}`);
    const trackedInfo = this.resolveDataTracking(firstBODInfo, step);
    const data = {
      url: STEP_NAME[step],
      body: trackedInfo,
      token: trackingToken,
    };
    (step === STEPS.USER_INFO || step === STEPS.SOCIAL_INFO) &&
      trackingInfo(data);
  };

  resolveDataTracking = (data, step) => {
    switch (step) {
      case STEPS.USER_INFO: {
        const { firstName, lastName, middleName, gender } = data;
        const dob = get(data, 'dayOfBirth');
        const dayOfBirth = isString(dob)
          ? convertDate(new Date(dob), 'YYYY-MM-DD')
          : convertDate(dob, 'YYYY-MM-DD');

        return {
          dayOfBirth,
          firstName,
          gender,
          lastName,
          middleName,
        };
      }
      case STEPS.SOCIAL_INFO: {
        const {
          monthlyIncome,
          monthlyPayment,
          identificationType,
          drivingLicence,
          idCard,
          familyBook,
        } = data;
        const rawData = {
          drivingLicence,
          familyBook,
          idCard,
          identificationType,
          monthlyIncome,
          otherMonthlyInstalment: monthlyPayment,
        };

        rawData[UNIFIED_ID_TYPE[identificationType]] = '';

        return rawData;
      }
      default: {
        return {};
      }
    }
  };

  render() {
    const { step = STEPS.WELCOME_PAGE, params, loaderStatus } = this.props;
    const { isOpenModal, modalId } = this.state;
    const ContentComponent = get(STEP_COMPONENT, step);
    const popup = { open: this.onOpenModal, close: this.onCloseModal };
    const isFirstAndLastStep = [0, 7].includes(step);

    return (
      <div className={s.homeWrapper}>
        <DesktopDesign>
          <div className={s.layoutWrapper}>
            <div>
              {!isFirstAndLastStep && (
                <HeaderBar
                  title={get(HEADER_TITLES, step)}
                  onBackClick={this.onClickBack}
                  onNextClick={this.onClickNext}
                  hideNextButton
                  className={s.headerbar}
                />
              )}
              <ContentComponent
                goToNextStep={this.goToNextStep}
                globalPopup={popup}
                params={params}
                onNextClick={this.onClickNext}
              />
            </div>
          </div>
          {loaderStatus && <FullLoadingCycle className={s.loading} />}
          <NextButton
            onNextClick={this.onClickNext}
            hideNextButton={isFirstAndLastStep}
          />
        </DesktopDesign>
        <NextButton
          mobile
          onNextClick={this.onClickNext}
          hideNextButton={isFirstAndLastStep}
        />
        <CallButton
          className={classnames(s.callBtn, step === 0 && s.callBtnSpec)}
        />

        <Modal
          onOpen={this.onOpenModal}
          onClose={this.onCloseModal}
          isOpen={isOpenModal}
          footer={
            <div className={s.modalFooter} onClick={this.onCloseModal}>
              {(!modalId || modalId === INVALID_SA_CODE) && (
                <h4>{MESSAGES.OK}</h4>
              )}

              {modalId === SUBMIT_APPLICATION_FAIL && <h4>{AGREE}</h4>}
            </div>
          }
        >
          {!modalId && <p className={s.errorText}>{MESSAGES.GLOBAL_ERROR}</p>}

          {modalId === SUBMIT_APPLICATION_FAIL && (
            <div className={s.modalBody}>
              <h3>Lưu ý</h3>

              <p>Đơn vay chưa được đăng ký thành công.</p>

              <p>
                Quý khách vui lòng kiểm tra lại thông tin đã nhập, hoặc liên hệ
                nhân viên http://www.bocvietnam.com gần nhất để được hỗ trợ.
              </p>
            </div>
          )}

          {modalId === INVALID_SA_CODE && (
            <div className={s.modalBody}>
              <h3>Lưu ý</h3>
              <p className={s.modalText}>Mã SA không hợp lệ</p>
            </div>
          )}
        </Modal>
      </div>
    );
  }
}

const mapStateToProps = ({ clz }) => ({
  formStatus: clz.formStatus,
  step: clz.step,
  trackingToken: clz.trackingToken,
  firstBODInfo: clz.firstBODInfo,
  loaderStatus: clz.loaderStatus,
});

const mapDispatchToProps = dispatch => ({
  updateFormStatus(value) {
    dispatch(setFormStatus(value));
  },
  updateStep(step) {
    dispatch(updateStep(step));
  },
  onChange1BODInfo(label, value) {
    dispatch(set1BODInfo({ label, value }));
  },
});

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withStyles(s),
);

export default enhance(FirstBOD);

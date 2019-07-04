import React, { Component, Fragment } from 'react';
import { arrayOf, object, func, number, noop, objectOf, any } from 'prop-types';
import classnames from 'classnames';
import { isEmpty, get, omit, isString } from 'lodash';
import { compose } from 'redux';
import { connect } from 'react-redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { FORM_STATUS, LOADER_STATUS } from 'constants/common';
import { Input, IconButton } from 'homecredit-ui';
import Modal from 'components/common/Modal';

import { formatDateString, convertDate } from 'utils/time';
import { formatCurrency } from 'utils/string';
import {
  setFormStatus,
  getCashOfferSuccess,
  getCashOffer,
  createApplication,
  updateLoading,
  set1BODInfo,
  trackingInfo,
  clear1BODInfo,
} from 'actions/clz';
import { verifySACode } from 'actions/sale';
import { sendGAEvent } from 'utils/ga';

import {
  GENDER,
  CREDIT_AMOUNT_FIELDS,
  TENOR_FIELDS,
  SUMMARY_TEXTS,
  SA_CODE_INPUT,
  SUBMIT_APPLICATION_FAIL,
  INVALID_SA_CODE,
  STEPS,
  STEP_NAME,
  UNIFIED_ID_TYPE,
} from './constant';
import { getBestOffer } from './utils';
import s from './Summary.css';

class Summary extends Component {
  static propTypes = {
    information: arrayOf(object).isRequired,
    userInfo: arrayOf(object).isRequired,
    creditAmount: number.isRequired,
    tenor: number.isRequired,
    updateLoadingStatus: func,
    updateFormStatus: func,
    formStatus: number,
    getOfferSuccess: func,
    globalPopup: objectOf(any),
    firstBODInfo: objectOf(any),
    onChange1BODInfo: func,
    reset1BODInfo: func,
    partnerCode: arrayOf(object).isRequired,
  };

  static defaultProps = {
    updateFormStatus: null,
    formStatus: 0,
    getOfferSuccess: noop,
    globalPopup: {},
    firstBODInfo: {},
    updateLoadingStatus: noop,
    onChange1BODInfo: noop,
    reset1BODInfo: noop,
  };

  constructor(props) {
    super(props);
    this.state = {
      isOpenModal: false,
      isOpenSAModal: false,
      isExcludeInsurance: false,
    };
  }

  /**
   * Update Form Status to initial after render
   *
   * @return {void}
   */
  componentDidMount() {
    this.props.updateFormStatus(FORM_STATUS.INITIAL);

    sendGAEvent('Visit_summary', 'Summary', 'Summary');
  }

  /**
   * Check if formStatus !== nextStatus, go to congratulation page
   *
   * @return {void}
   */
  async componentWillReceiveProps(nextProps) {
    const {
      formStatus,
      globalPopup,
      updateLoadingStatus,
      updateFormStatus,
      goToNextStep,
      trackedInfo,
      trackingToken,
      reset1BODInfo,
    } = this.props;
    const { formStatus: nextStatus, firstBODInfo } = nextProps;

    if (formStatus === FORM_STATUS.INITIAL && formStatus !== nextStatus) {
      sendGAEvent('Submit_application', 'Submit_application', 'Summary');

      // Compress image before submit to server to reduce request limit
      const applicationData = omit(firstBODInfo, [
        'offerList',
        firstBODInfo[SA_CODE_INPUT.name] || SA_CODE_INPUT.name,
      ]);
      const { userImage: images, dayOfBirth } = applicationData;
      // Assign session Id
      applicationData.sessionID = document
        .getElementById('sessionID')
        .getAttribute('value');

      applicationData.dayOfBirth = isString(dayOfBirth)
        ? convertDate(new Date(dayOfBirth), 'YYYY-MM-DD')
        : convertDate(dayOfBirth, 'YYYY-MM-DD');
      updateLoadingStatus(LOADER_STATUS.ON);
      applicationData.userImage = images[Object.keys(images)[0]];
      updateLoadingStatus(LOADER_STATUS.OFF);

      // Check SA Code before submit
      if (applicationData.retailAgent) {
        const saCodeResult = await verifySACode(applicationData.retailAgent);
        if (!saCodeResult.valid) {
          globalPopup.open(INVALID_SA_CODE);
          updateFormStatus(FORM_STATUS.INITIAL);
          return;
        }
      }

      updateLoadingStatus(LOADER_STATUS.ON);
      applicationData[UNIFIED_ID_TYPE[firstBODInfo.identificationType]] = '';
      createApplication({
        firstBODInfo: applicationData,
        token: trackingToken,
      }).then(response => {
        updateLoadingStatus(LOADER_STATUS.OFF);
        const data = {
          url: STEP_NAME[STEPS.SUMMARY],
          token: trackingToken,
          body: {
            contractId: '',
            ...trackedInfo,
          },
        };
        if (isEmpty(response.errors) && isEmpty(response.errorMessages)) {
          sendGAEvent(
            'Submit_application_successfully',
            'Application',
            'Submit Result',
          );

          goToNextStep();
          data.body.contractId = get(response, 'applicationCode');
          reset1BODInfo();
        } else {
          sendGAEvent(
            'Submit_application_unsuccessfully',
            'Application',
            'Submit Result',
          );
          updateFormStatus(FORM_STATUS.INITIAL);
          globalPopup.open(SUBMIT_APPLICATION_FAIL);
        }
        trackingInfo(data);
      });
    }
  }

  /**
   * Update state when modal is opened
   */
  onOpenModal = () => {
    this.setState({
      isOpenModal: true,
    });

    sendGAEvent('View_insurance', 'Insurance', 'Summary');
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
   * Update state when modal is opened
   */
  onOpenSAModal = e => {
    // detect if input is focused, stop it
    if (document.activeElement === e.target) return;

    this.setState({
      isOpenSAModal: true,
    });
    e.target.blur();
    e.preventDefault();
  };

  /**
   * Update state when modal is closed
   */
  onCloseSAModal = () => {
    this.setState({
      isOpenSAModal: false,
    });
  };

  /**
   * On Button Cancel on click
   */
  onAcceptModal = () => {
    this.setState({
      isOpenSAModal: false,
    });
    this.saInput.focus();
  };

  saRef = ref => {
    this.saInput = ref;
  };

  /**
   * Get Offer list and re-calculate the best offer base on the selected tenor
   *
   * @param  {bool} excludeInsurance Exclude the insurance for offer
   * @return {void} {description}
   */
  updateBestOffer = () => {
    const {
      creditAmount,
      tenor,
      getOfferSuccess,
      globalPopup,
      updateLoadingStatus,
      partnerCode,
    } = this.props;
    const { isExcludeInsurance } = this.state;

    this.onCloseModal();

    updateLoadingStatus(LOADER_STATUS.ON);
    getCashOffer(creditAmount, !isExcludeInsurance, partnerCode).then(
      response => {
        updateLoadingStatus(LOADER_STATUS.OFF);
        if (isEmpty(response.errors)) {
          // Update best offer
          const bestOffer = getBestOffer(response.data, tenor);
          getOfferSuccess(bestOffer);

          this.setState({ isExcludeInsurance: !isExcludeInsurance });
          sendGAEvent('Remove_insurance', 'Insurance', 'Summary');
        } else {
          globalPopup.open();
        }
      },
    );
  };

  handleOnSACodeChange = e => {
    const { onChange1BODInfo } = this.props;

    onChange1BODInfo(SA_CODE_INPUT.name, e.target.value);
  };

  handleBlur = () => sendGAEvent('Input_SA_Code', 'SA_code', 'Summary');

  render() {
    const { information, userInfo, firstBODInfo } = this.props;
    const { isOpenModal, isOpenSAModal, isExcludeInsurance } = this.state;
    const { sa, partnerCode, tipperCode } = firstBODInfo;
    return (
      <div className="container">
        <div className="row center">
          <div className={classnames('col-md-10 col-12', s.summary)}>
            <h4 className={s.firstHeading}>Thông tin khoản vay</h4>

            <div className="row">
              {information.map(({ name, value, className, helpIcon }) => (
                <div key={name} className={classnames(s.item, className)}>
                  <div className={s.itemHeading}>
                    {name}
                    {helpIcon && (
                      <IconButton
                        onClick={this.onOpenModal}
                        icon="help_outline"
                        primary
                        smallIcon
                        className={s.helpIcon}
                      />
                    )}
                  </div>
                  <div>{value}</div>
                </div>
              ))}
            </div>
            <h4 className={s.secondHeading}>Thông tin cá nhân</h4>

            <div className="row">
              {userInfo.map(({ name, value, className }) => (
                <div key={name} className={classnames(s.item, className)}>
                  <div className={s.itemHeading}>{name}</div>
                  <div>{value}</div>
                </div>
              ))}

              <div className="col-6">
                <h4 className={classnames(s.secondHeading, s.saHeading)}>
                  Dành cho http://www.bocvietnam.com
                </h4>
              </div>
              <div className="col-6">
                <Input
                  readOnly={!!partnerCode || !!sa}
                  disabled={!!partnerCode || !!sa}
                  inputRef={this.saRef}
                  label={SA_CODE_INPUT.label}
                  name={SA_CODE_INPUT.name}
                  value={
                    tipperCode ||
                    ((!partnerCode && firstBODInfo[SA_CODE_INPUT.name]) || null)
                  }
                  onChange={this.handleOnSACodeChange}
                  onBlur={this.handleBlur}
                  onMouseDown={this.onOpenSAModal}
                  className={s.inputSa}
                />
              </div>
            </div>
          </div>

          <Modal
            onOpen={this.onOpenModal}
            onClose={this.onCloseModal}
            isOpen={isOpenModal}
            footer={
              <div className={s.modalFooter}>
                <div className={s.halfButton} onClick={this.onCloseModal}>
                  <h5>{SUMMARY_TEXTS.AGREE}</h5>
                </div>
                <div
                  className={classnames(s.halfButton, s.notAcceptButton)}
                  onClick={this.updateBestOffer}
                >
                  <h5>
                    {isExcludeInsurance
                      ? SUMMARY_TEXTS.ADD_INSURANCE
                      : SUMMARY_TEXTS.DECLINED}
                  </h5>
                </div>
              </div>
            }
          >
            <div className={s.modalBody}>
              <h3>{SUMMARY_TEXTS.INSURANCE_TITLE}</h3>
              <Fragment>
                <p>{SUMMARY_TEXTS.INSURANCE_CONTENT_1}</p>
                <p>{SUMMARY_TEXTS.INSURANCE_CONTENT_2}</p>
                <p>{SUMMARY_TEXTS.INSURANCE_CONTENT_3}</p>
              </Fragment>
            </div>
          </Modal>

          <Modal
            onOpen={this.onOpenSAModal}
            onClose={this.onCloseSAModal}
            isOpen={isOpenSAModal}
            footer={
              <div className={s.modalFooter}>
                <div className={s.halfButton} onClick={this.onCloseSAModal}>
                  <h4>{SUMMARY_TEXTS.SA_CANCEL_BTN}</h4>
                </div>
                <div className={s.halfButton} onClick={this.onAcceptModal}>
                  <h4>{SUMMARY_TEXTS.SA_ACCEPT_BTN}</h4>
                </div>
              </div>
            }
          >
            <div className={s.modalBody}>
              <h3>{SUMMARY_TEXTS.SA_POPUP_TITLE}</h3>
              <Fragment>
                <p>{SUMMARY_TEXTS.SA_POPUP_1}</p>
                <p>{SUMMARY_TEXTS.SA_POPUP_2}</p>
              </Fragment>
            </div>
          </Modal>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ clz }) => {
  const {
    firstName,
    middleName,
    lastName,
    gender,
    dayOfBirth,
    idCard,
    creditAmount,
    tenor,
    bestOffer,
    retailAgent,
    tipperCode,
  } = clz.firstBODInfo;
  const { formStatus, trackingToken } = clz;
  const trackedInfo = {
    saCode: retailAgent,
    tipperCode,
    insuranceInfo: get(bestOffer, 'celOffer.totalInsurancePremium.amount'),
  };
  const information = [
    {
      name: CREDIT_AMOUNT_FIELDS.label,
      value: formatCurrency(creditAmount * 1000000),
      className: 'col-6',
    },
    {
      name: TENOR_FIELDS.label,
      value: `${tenor} tháng`,
      className: 'col-6',
    },
    {
      name: SUMMARY_TEXTS.INSURANCE_AMOUNT,
      value: formatCurrency(
        get(bestOffer, 'celOffer.totalInsurancePremium.amount') || 0,
      ),
      helpIcon: true,
      className: 'col-md-6 col-12',
    },
    {
      name: SUMMARY_TEXTS.MONTHTLY_FEE,
      value: formatCurrency(
        get(bestOffer, 'celOffer.monthlyInstallmentAmount.amount') || 0,
      ),
      className: 'col-md-6 col-12',
    },
  ];

  const userInfo = [
    {
      name: SUMMARY_TEXTS.FULL_NAME,
      value: `${lastName} ${middleName} ${firstName}`,
      className: 'col-6',
    },
    {
      name: SUMMARY_TEXTS.GENDER,
      value: GENDER[gender],
      className: 'col-6',
    },
    {
      name: SUMMARY_TEXTS.DATEOFBIRTH,
      value: formatDateString(dayOfBirth),
      className: 'col-md-6 col-12',
    },
    {
      name: SUMMARY_TEXTS.ID_CARD,
      value: idCard,
      className: 'col-md-6 col-12',
    },
  ];

  return {
    firstBODInfo: clz.firstBODInfo,
    information,
    userInfo,
    formStatus,
    creditAmount,
    tenor,
    trackedInfo,
    trackingToken,
    partnerCode: clz.firstBODInfo.partnerCode,
  };
};

const mapDispatchToProps = dispatch => ({
  updateFormStatus(value) {
    dispatch(setFormStatus(value));
  },
  getOfferSuccess(response) {
    dispatch(getCashOfferSuccess(response));
  },
  updateLoadingStatus(status) {
    dispatch(updateLoading(status));
  },
  onChange1BODInfo(label, value) {
    dispatch(set1BODInfo({ label, value }));
  },
  reset1BODInfo() {
    dispatch(clear1BODInfo());
  },
});

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withStyles(s),
);

export default enhance(Summary);

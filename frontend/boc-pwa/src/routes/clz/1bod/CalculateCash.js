/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React, { Component } from 'react';
import { compose } from 'redux';
import { objectOf, any, func, number, arrayOf, object } from 'prop-types';
import { connect } from 'react-redux';
import { withFormik } from 'formik';
import { noop, pick, get, isEmpty, isEqual, toNumber } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Slider, ButtonGroup } from 'homecredit-ui';

import { FORM_STATUS, LOADER_STATUS } from 'constants/common';
import { formatCurrency } from 'utils/string';
import { sendGAEvent } from 'utils/ga';

import {
  set1BODInfo,
  setFormStatus,
  getCashOfferSuccess,
  getCashOffer,
  updateLoading,
} from 'actions/clz';

import {
  CREDIT_AMOUNT_FIELDS,
  TENOR_FIELDS,
  CREDIT_AMOUNT_MIN,
  CREDIT_AMOUNT_MAX,
  CREDIT_AMOUNT_STEP,
} from './constant';
import { getBestOffer, getOfferRange, getOfferTenors } from './utils';
import s from './CalculateCash.css';

class CalculateCash extends Component {
  static propTypes = {
    formStatus: number,
    values: objectOf(any),
    setFieldValue: func,
    onChange1BODInfo: func,
    updateFormStatus: func,
    goToNextStep: func,
    updateLoadingStatus: func,
    getOfferSuccess: func,
    globalPopup: objectOf(any),
    bestOffer: objectOf(any),
    partnerCode: arrayOf(object).isRequired,
  };

  static defaultProps = {
    formStatus: FORM_STATUS.INITIAL,
    values: {},
    setFieldValue: noop,
    onChange1BODInfo: noop,
    updateFormStatus: noop,
    goToNextStep: noop,
    getOfferSuccess: noop,
    updateLoadingStatus: noop,
    globalPopup: {},
    bestOffer: {},
  };

  /**
   * Update Form Status to initial after render
   *
   * @return {void}
   */
  componentDidMount = () => {
    const { values } = this.props;
    const { offerList } = values;
    this.props.updateFormStatus(FORM_STATUS.INITIAL);

    // Make sure if this is the first time enter screen
    // make request for offer
    if (!offerList.length) {
      this.updateCashOffer(values.creditAmount);
    }

    sendGAEvent('Visit_loan_offer', 'Loan_offer', 'Loan Offer');
  };

  /**
   * Validate when form status is changed from Initial to Need validate
   *
   * @return {void}
   */
  componentWillReceiveProps(nextProps) {
    const { formStatus, goToNextStep } = this.props;
    const { formStatus: nextStatus } = nextProps;

    formStatus === FORM_STATUS.INITIAL &&
      formStatus !== nextStatus &&
      goToNextStep();
  }

  shouldComponentUpdate(nextProps) {
    return !isEqual(this.props, nextProps);
  }

  /**
   * Update Form Field Credit Amount
   *
   * @return {void}
   */
  onCreditAmountChange = value => {
    const creditAmount = get(this.props, 'values.creditAmount');

    if (value !== creditAmount) {
      this.updateFormInfo(CREDIT_AMOUNT_FIELDS.name, value);
    }
  };

  /**
   * Update Form Field Credit Amount Complete
   *
   * @return {void}
   */
  onCreditAmountChangeComplete = () => {
    const creditAmount = get(this.props, 'firstBODInfo.creditAmount');

    this.updateCashOffer(creditAmount);

    sendGAEvent('Select_credit_amount', 'Credit_amount', 'Loan Offer');
  };

  /**
   * Update Form Field Credit Amount
   *
   * @return {void}
   */
  onTenorChange = value => {
    const { values, getOfferSuccess } = this.props;
    const newValue = toNumber(value);
    this.updateFormInfo(TENOR_FIELDS.name, newValue);

    const bestOffer = getBestOffer(values.offerList, newValue);
    getOfferSuccess(bestOffer);

    sendGAEvent('Select_tenor', 'Tenor', 'Loan Offer');
  };

  /**
   * Update Offer List and calculate best offer
   *
   * @return {void}
   */
  updateCashOffer = creditAmount => {
    const {
      globalPopup,
      getOfferSuccess,
      updateLoadingStatus,
      partnerCode,
    } = this.props;

    updateLoadingStatus(LOADER_STATUS.ON);
    getCashOffer(creditAmount, false, partnerCode).then(response => {
      if (isEmpty(response.errors)) {
        // Update offer list
        this.updateFormInfo('offerList', response.data);

        // Update minimum tenor
        const offerRange = getOfferRange(response.data);
        this.updateFormInfo(TENOR_FIELDS.name, offerRange.min);

        // Update best offer
        const newMin = offerRange.min;
        const bestOffer = getBestOffer(response.data, newMin);
        getOfferSuccess(bestOffer);
      } else {
        globalPopup.open();
      }

      updateLoadingStatus(LOADER_STATUS.OFF);
    });
  };

  /**
   * Update Form Field Value & Redux State
   *
   * @param  {String} name  Field Name
   * @param  {String} value Field Value
   *
   * @return {Void}
   */
  updateFormInfo = (name, value) => {
    const { setFieldValue, onChange1BODInfo } = this.props;

    // Update Form Value
    setFieldValue(name, value);

    // Update Redux state
    onChange1BODInfo(name, value);
  };

  render() {
    const { values, bestOffer } = this.props;
    const { creditAmount, tenor, offerList } = values;
    const monthlyPayment =
      get(bestOffer, 'celOffer.monthlyInstallmentAmount.amount') || 0;

    return (
      <div className="container">
        <div className="row center">
          <div className="col-md-10 col">
            <div className={s.range}>
              <span>10 triệu</span>
              <div className={s.center}>
                Số tiền vay: &nbsp;{' '}
                <span className={s.amount}>{creditAmount} triệu</span>
              </div>
              <span>{CREDIT_AMOUNT_MAX} triệu</span>
            </div>
            <Slider
              min={CREDIT_AMOUNT_MIN}
              max={CREDIT_AMOUNT_MAX}
              step={CREDIT_AMOUNT_STEP}
              value={creditAmount}
              onChange={this.onCreditAmountChange}
              onChangeComplete={this.onCreditAmountChangeComplete}
            />
            <div className={s.range}>
              <span />
              <div className={s.center}>
                Thời gian vay: &nbsp;{' '}
                <span className={s.amount}>{tenor} tháng</span>
              </div>
              <span />
            </div>
            <ButtonGroup
              className={s.tenorGroup}
              buttonClassname={s.tenorButton}
              value={tenor}
              buttons={getOfferTenors(offerList)}
              onChange={this.onTenorChange}
            />
            <div className={s.totalContainer}>
              <div className={s.total}>
                Số tiền trả góp hàng tháng
                <div className={s.amount}>{formatCurrency(monthlyPayment)}</div>
              </div>
              <div className={s.helpText}>
                Số tiền trả góp hàng tháng được tạm tính dựa theo nhu cầu vay
                bạn đã chọn
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ clz }) => ({
  firstBODInfo: clz.firstBODInfo,
  formStatus: clz.formStatus,
  bestOffer: clz.firstBODInfo.bestOffer,
  partnerCode: clz.firstBODInfo.partnerCode,
});

const mapDispatchToProps = dispatch => ({
  onChange1BODInfo(label, value) {
    dispatch(set1BODInfo({ label, value }));
  },
  updateFormStatus(value) {
    dispatch(setFormStatus(value));
  },
  getOfferSuccess(response) {
    dispatch(getCashOfferSuccess(response));
  },
  updateLoadingStatus(status) {
    dispatch(updateLoading(status));
  },
});

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withFormik({
    validateOnBlur: false,
    mapPropsToValues: props =>
      pick(props.firstBODInfo, ['creditAmount', 'tenor', 'offerList']),
  }),
  withStyles(s),
);

export default enhance(CalculateCash);

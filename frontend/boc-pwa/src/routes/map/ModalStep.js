import React, { PureComponent, Fragment } from 'react';
import { number, shape, string, func, bool } from 'prop-types';
import { sendEmail } from 'actions/email';
import { MESSAGES } from 'constants/common';
import classnames from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Select, Option } from 'components/common/Select';
import { FullLoading, Cycle, ButtonGroup, Modal } from 'homecredit-ui';

import { chain, noop, slice } from 'lodash';

import {
  HOURS_APPOINTMENT,
  MODAL_CONTENT,
  CANCEL_BTN,
  CONGRATULATION,
  STEP_ORDER_HOUR,
  STEP_SUMMARY,
  ERROR,
  ORDER_DAY_PLACEHOLDER,
  LATEST_HOUR,
} from './constant';
import {
  convertDate,
  milisecondOfDay,
  getDateFromString,
} from '../../utils/time';
import s from './ModalStep.css';

class ModalStep extends PureComponent {
  static propTypes = {
    shop: shape({
      Address: string,
      Identity: string,
      Latitude: number,
      Longitude: number,
      ShopCode: string,
      Title: string,
    }),
    onClose: func,
    isOpen: bool,
    contractCreateAt: string,
  };

  static defaultProps = {
    shop: null,
    onClose: noop,
    isOpen: false,
    contractCreateAt: '',
  };

  constructor(props) {
    super(props);

    this.state = {
      step: STEP_ORDER_HOUR,
      orderHour: '',
      orderDay: this.getCurrentDate(),
      isLoading: false,
    };
  }

  onChangeDay = (name, { value }) => {
    this.setState({
      orderDay: value,
      orderHour: '',
    });
  };

  /**
   * Listener for change Hours
   * @param  {Any} value of value order hours
   * @return undefined
   */
  onChangeHours = value => {
    this.setState({
      orderHour: value,
    });
  };

  getCurrentDate = () => {
    if (new Date().getHours() >= LATEST_HOUR) {
      return convertDate(new Date(Date.now() + milisecondOfDay), 'DD/MM/YYYY');
    }
    return convertDate(new Date(), 'DD/MM/YYYY');
  };

  /**
   * Close modal
   */
  closeModal = () => {
    const { onClose } = this.props;

    this.setState({
      step: STEP_ORDER_HOUR,
      orderHour: '',
      orderDay: this.getCurrentDate(),
    });
    onClose();
  };

  /**
   * Handle submit appointment
   */
  handleSubmit = async () => {
    // ga
    window.ga('send', 'event', {
      eventCategory: 'POS Map',
      eventAction: 'Submit_appointment',
      eventLabel: 'Appointment',
    });

    const { shop, contractId } = this.props;
    const { orderHour, orderDay } = this.state;

    this.setState({ isLoading: true });
    const response = await sendEmail(contractId, {
      shopCode: shop.ShopCode,
      appointmentTime: `${orderDay} - ${orderHour}`,
      address: shop.Address,
      shopName: shop.Title,
    });

    if (response.errors) {
      this.setState({
        step: ERROR,
        isLoading: false,
      });
      return;
    }

    this.setState({
      isLoading: false,
      step: STEP_SUMMARY,
    });
  };

  isToday = () => {
    const { orderDay } = this.state;

    if (!orderDay) return false;
    return orderDay === convertDate(new Date(), 'DD/MM/YYYY');
  };

  /**
   * render modal body
   * @return {ReactDOM}
   */
  renderBodyModal = () => {
    const { step, orderHour, orderDay } = this.state;
    const { Address, Title } = this.props.shop;

    if (step === STEP_ORDER_HOUR) {
      return (
        <Fragment>
          <div className={s.modalBody}>
            <h4 className={s.modalTitle}>{Title}</h4>
            <p>{Address}</p>
            {this.renderDateAppointment()}
            {this.renderWorkingHours()}
          </div>
        </Fragment>
      );
    }

    if (step === STEP_SUMMARY) {
      return (
        <div className={s.modalBody}>
          <h3 className={s.modalTitle}>{CONGRATULATION}</h3>
          <p className={s.summaryMessage}>
            Quý Khách đã đặt hẹn thành công với nhân viên{' '}
            <span className={s.homecreditHightlight}>http://www.bocvietnam.com</span> tại{' '}
            <strong>{Address}</strong>, vào khung giờ{' '}
            <span className={s.orderDate}>{orderHour}</span> ngày{' '}
            <span className={s.orderDate}>{orderDay}</span>
          </p>
        </div>
      );
    }

    if (step === ERROR) {
      return <p className={s.error}>{MESSAGES.GLOBAL_ERROR}</p>;
    }

    return null;
  };

  /**
   * render modal footer
   * @return {ReactDOM}
   */
  renderFooterModal = () => {
    const { step, orderHour } = this.state;
    const classNameBtnSubmit = classnames(
      s.modalBtn,
      !orderHour && s.modalBtnDisable,
    );

    if (step === STEP_ORDER_HOUR) {
      return (
        <div className={s.modalFooter}>
          <button className={s.modalBtn} onClick={this.closeModal}>
            {CANCEL_BTN}
          </button>

          <button className={classNameBtnSubmit} onClick={this.handleSubmit}>
            {MODAL_CONTENT[step].FOOTER_TITLE}
          </button>
        </div>
      );
    }

    return (
      <div className={s.modalFooter}>
        <button onClick={this.closeModal} className={s.modalBtn}>
          {MESSAGES.OK}
        </button>
      </div>
    );
  };

  renderDateAppointment = () => {
    const { contractCreateAt } = this.props;
    const { orderDay } = this.state;

    const dates = slice(Array(7))
      .map((v, index) => {
        const today = new Date();
        const appointmentDate = new Date(
          getDateFromString(contractCreateAt).getTime() +
            milisecondOfDay * index,
        );
        appointmentDate.setHours(LATEST_HOUR);

        const isLate = appointmentDate.getTime() < today.getTime();

        return { date: convertDate(appointmentDate, 'DD/MM/YYYY'), isLate };
      })
      .filter(date => !date.isLate)
      .map(({ date }) => ({
        value: date,
        label: date,
      }));

    return (
      <Select
        value={orderDay}
        placeholder={ORDER_DAY_PLACEHOLDER}
        onChange={this.onChangeDay}
        className={s.selectDate}
        classNameWrapperOptions={s.selectWapperOptions}
      >
        {dates.map(({ value, label }) => (
          <Option value={value} label={label} key={value} />
        ))}
      </Select>
    );
  };

  /**
   * Render RadioGroup
   * @return {ReactDOM}
   */
  renderWorkingHours = () => {
    const { orderHour } = this.state;

    const orderButtons = HOURS_APPOINTMENT.map(({ start, end }) => {
      const isToday = this.isToday();
      const endHourWorking = chain(end)
        .split(':')
        .head()
        .value();
      const isAvailable = endHourWorking > new Date().getHours();
      const hourString = `${start} - ${end}`;

      return {
        content: hourString,
        title: hourString,
        value: hourString,
        disabled: isToday && !isAvailable,
      };
    });

    return (
      <React.Fragment>
        <ButtonGroup
          value={orderHour}
          buttons={orderButtons}
          onChange={this.onChangeHours}
          className={s.btnGroup}
          buttonClassname={s.btnGroupItem}
        />
      </React.Fragment>
    );
  };

  render() {
    const { isOpen } = this.props;
    const { isLoading } = this.state;
    const FullLoadingCycle = FullLoading(Cycle);

    return (
      <Fragment>
        <Modal
          isOpen={isOpen}
          onClose={this.closeModal}
          footer={this.renderFooterModal()}
        >
          {isOpen ? this.renderBodyModal() : null}
        </Modal>
        {isLoading && <FullLoadingCycle fullPage />}
      </Fragment>
    );
  }
}

export default withStyles(s)(ModalStep);

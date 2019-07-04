import React, { Component } from 'react';
import { string, func } from 'prop-types';
import { get, noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { RadioButton } from 'homecredit-ui';
import Modal from 'components/common/Modal';

import s from './FamilyBook.css';
import { POPUP_CONTENT } from './constant';

class SocialInfo extends Component {
  static propTypes = {
    type: string.isRequired,
    value: string.isRequired,
    onChange: func,
    groupValue: string,
  };

  static defaultProps = {
    onChange: noop,
    groupValue: '',
  };

  state = { isOpen: false };

  onOpenModal = () => {
    // Don't call onChange when modal is open
    if (this.state.isOpen) return;

    const { onChange, value } = this.props;

    this.setState({
      isOpen: true,
    });
    onChange && onChange(value);
  };

  onCloseModal = () => {
    this.setState({
      isOpen: false,
    });
  };

  render() {
    const { type, value, groupValue } = this.props;
    const content = get(POPUP_CONTENT, type);

    return (
      <Modal
        onOpen={this.onOpenModal}
        onClose={this.onCloseModal}
        isOpen={this.state.isOpen}
        toggleButton={
          <RadioButton
            className={s.radio}
            label={type}
            value={value}
            checked={groupValue === value}
          />
        }
        footer={
          <div
            className={s.footer}
            onKeyDown={this.onCloseModal}
            onClick={this.onCloseModal}
            role="button"
            tabIndex="0"
          >
            <h4>Tôi đã hiểu</h4>
          </div>
        }
      >
        <div className={s.content}>
          <h4 className={s.title}>{type}</h4>
          {content.map(item => (
            <span key={item.step}>
              {item.title && <h4>{`${item.step}. ${item.title}`}</h4>}
              <p>
                {item.content}
                {item.contentBold && <b>{item.contentBold}</b>}
                {item.contentNext && <span>{item.contentNext}</span>}
              </p>
              <img src={item.image} alt="sohokhau" />
            </span>
          ))}
        </div>
      </Modal>
    );
  }
}

export default withStyles(s)(SocialInfo);

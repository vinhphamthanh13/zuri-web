import { BubbleChart, FilterVintage, Reorder, ShowChart } from 'constants/svg';
import React, { Component } from 'react';
import { string, func, bool, node } from 'prop-types';
import { noop } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './BocTabs.css';

class BocTabs extends Component {
  static propTypes = {
    label: string.isRequired,
    className: string,
    disabled: bool,
    onClick: func,
    children: node,
    type: string,
  };

  static defaultProps = {
    className: '',
    disabled: false,
    onClick: noop,
    children: null,
    type: '',
  };

  render() {
    const { className, onClick, disabled, type } = this.props;
    const props = {
      className: disabled
        ? `${s.button} ${className} ${s.buttonDisabled}`
        : `${s.button} ${className}`,
      onClick: disabled ? noop : onClick,
      type: disabled ? 'button' : type,
    };

    return (
      <div className={s.toolBar}>
        <div className={s.tab} onClick={this.handleTabClick('home')}>
          <Reorder hexColor="#2e4698" />
          <div>Tổng quan</div>
        </div>
        <div className={s.tab} onClick={this.handleTabClick('report')}>
          <ShowChart hexColor="#2e4698" />
          <div>Báo cáo</div>
        </div>
        <div className={s.tab} onClick={this.handleTabClick('activity')}>
          <BubbleChart hexColor="#2e4698" />
          <div>Hoạt động</div>
        </div>
        <div className={s.tab} onClick={this.handleTabClick('shop')}>
          <FilterVintage hexColor="#2e4698" />
          <div>Cửa hàng</div>
        </div>
      </div>
    );
  }
}

export default withStyles(s)(BocTabs);

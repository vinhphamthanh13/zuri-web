import React, { Component } from 'react';
import { func } from 'prop-types';
import Header from 'components/Header';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './ShopDetail.css';

class ShopDetail extends Component {
  static propTypes = {
    onClose: func.isRequired,
  };

  static defaultProps = {
  };

  state = {};

  render() {
    const { onClose } = this.props;
    return (
      <div className={s.container}>
        <Header
          title="Thông tin cửa hàng"
          iconLeft
          onClickLeft={onClose(false)}
        />
      </div>
    );
  }
}

export default withStyles(s)(ShopDetail);

import React, { Component } from 'react';
import Header from 'components/Header';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Shops.css';

class Shops extends Component {
  static propTypes = {};

  static defaultProps = {};

  state = {};

  render() {
    return (
      <div className={s.container}>
        <Header title="Danh sách cửa hàng" />
      </div>
    );
  }
}

export default withStyles(s)(Shops);

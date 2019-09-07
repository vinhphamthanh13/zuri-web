import React, { Component } from 'react';
import Header from 'components/Header';
import { connect } from 'react-redux';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { activationProps } from '../commonProps';
import s from './Shops.css';

class Shops extends Component {
  static propTypes = {};

  static defaultProps = {};

  state = {};

  render() {
    return (
      <div className={s.container}>
        <Header title="Danh sách cửa hàng" icon />
      </div>
    );
  }
}

const enhancer = [connect(activationProps.mapStateToProps), withStyles(s)];

export default compose(...enhancer)(Shops);

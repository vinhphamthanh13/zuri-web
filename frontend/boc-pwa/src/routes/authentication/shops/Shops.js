import React, { Component } from 'react';
import { get } from 'lodash';
import { connect } from 'react-redux';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { EDIT, START, LS_SHOP_ID } from 'constants/common';
import { ROUTER_URL } from 'constants/routerUrl';
import { resolveStringLength, navigateTo } from 'utils/browser';
import { Place, Send, Create } from 'constants/svg';
import { activationProps } from '../commonProps';
import s from './Shops.css';

class Shops extends Component {
  static propTypes = {
  };

  static defaultProps = {
  };

  state = {
    userDetail: {},
  };

  static getDerivedStateFromProps(props, state) {
    const { userDetail } = props;
    const { userDetail: cachedUserDetail } = state;
    if (userDetail !== cachedUserDetail) {
      return {
        userDetail,
      };
    }

    return null;
  }

  createShops = () => {
    const { userDetail } = this.state;
    const shopList = get(userDetail, 'listCuaHang') || [];
    return shopList.map(store => (
      <div key={store.id} className={s.store}>
        <div className={s.content}>
          <div className={s.title}>
            {resolveStringLength(store.cuaHangName, 18)}
          </div>
          <div className={s.items}>
            <div className={s.icon}>
              <Place size={32} />
            </div>
            <div className={s.address}>
              {resolveStringLength(store.address, 78)}
            </div>
          </div>
        </div>
        <div className={s.actions}>
          <div className={s.cta}>
            <Create size={24} />
            <div className={s.ctaText}>{EDIT}</div>
          </div>
          <div className={s.cta} onClick={this.handleStartShop(store.id)}>
            <Send size={24} />
            <div className={s.ctaText}>{START}</div>
          </div>
        </div>
      </div>
    ));
  };

  handleStartShop = shopId => () => {
    console.log('from shops', shopId);
    navigateTo(ROUTER_URL.TABS.SHOP, { [LS_SHOP_ID]: shopId });
  };

  render() {
    return this.createShops();
  }
}

const enhancer = [connect(activationProps.mapStateToProps), withStyles(s)];

export default compose(...enhancer)(Shops);

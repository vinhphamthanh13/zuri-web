import React from 'react';
import { func, string, objectOf, any } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { blockNavigation, navigateTo } from 'utils/browser';
import uuidv1 from 'uuid/v1';
import { chunk, get } from 'lodash';
import { SHOP } from 'constants/shop';
import Empty from 'components/Empty';
import Button from 'components/Button';
import { gray } from 'constants/colors';
import Modal from 'components/Modal';
import { Store, PhoneIphone, Place, ArrowForward } from 'constants/svg';
import { formatStringLength } from 'utils/string';
import { ACCESS_DENIED, CHANGE_STORE } from 'constants/common';
import { ROUTER_URL } from 'constants/routerUrl';
import ShopDetail from './components/ShopDetail';
import { shopsProps } from './commonProps';
import s from './Shop.css';

class Shop extends React.Component {
  static propTypes = {
    dispatchGettingStoreInfo: func.isRequired,
    dispatchUpdatingStoreInfo: func.isRequired,
    accessToken: string,
    userDetail: objectOf(any),
  };

  static defaultProps = {
    accessToken: '',
    userDetail: {},
  };

  state = {
    isOpenShopDetail: false,
    selectedShopId: null,
    isChangingStore: false,
  };

  static getDerivedStateFromProps(props, state) {
    const { selectedShopId, gettingShopInfo } = props;
    const {
      selectedShopId: cachedSelectedShopId,
      gettingShopInfo: cachedGettingShopInfo,
    } = state;
    if (
      selectedShopId !== cachedSelectedShopId ||
      gettingShopInfo !== cachedGettingShopInfo
    ) {
      return {
        selectedShopId,
        gettingShopInfo,
      };
    }

    return null;
  }

  componentDidMount() {
    const { dispatchGettingStoreInfo, accessToken } = this.props;
    const { selectedShopId } = this.state;
    if (accessToken && selectedShopId)
      dispatchGettingStoreInfo(selectedShopId, accessToken);
  }

  componentWillUnmount() {
    if (this.unblockNavigation) this.unblockNavigation();
  }

  unblockNavigation = null;

  handleShowShopDetail = value => () => {
    this.setState({
      isOpenShopDetail: value,
    });
  };

  createMenu = () =>
    Object.keys(SHOP.CATEGORY).map(cat => (
      <div key={cat} className={s.shopCategory}>
        <div className={s.category}>{SHOP.CATEGORY[cat].NAME}</div>
        <div>
          {chunk(SHOP.CATEGORY[cat].MENU, 4).map(row => (
            <div className={s.row} key={uuidv1()}>
              {row.map(menu => (
                <div className={s.item} key={uuidv1()} onClick={menu.action}>
                  <div className={s.icon}>{menu.ICON}</div>
                  <div className={s.label}>{menu.NAME}</div>
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>
    ));

  handleChangeStore = value => () => {
    if (value) this.unblockNavigation = blockNavigation('Test change store');
    else this.unblockNavigation();
    this.setState({
      isChangingStore: value,
    });
  };

  handleConfirmChangingStore = () => {
    this.handleChangeStore(false)();
    navigateTo(ROUTER_URL.AUTH.SHOPS);
  };

  render() {
    const { dispatchUpdatingStoreInfo, accessToken, userDetail } = this.props;
    const { isOpenShopDetail, gettingShopInfo, isChangingStore } = this.state;
    const shopName = get(gettingShopInfo, 'cuaHangName');
    const phoneNumber = get(gettingShopInfo, 'phone');
    const managerPhone = get(gettingShopInfo, 'managerPhone');
    const shopAddress = get(gettingShopInfo, 'address');
    const shopList = get(userDetail, 'listCuaHang') || [];
    const shopCount = shopList.length;

    return !accessToken ? (
      <Empty message={ACCESS_DENIED} />
    ) : (
      <>
        {isOpenShopDetail && (
          <ShopDetail
            onClose={this.handleShowShopDetail}
            shopDetail={{ ...gettingShopInfo, accessToken }}
            updatingStore={dispatchUpdatingStoreInfo}
          />
        )}
        {isChangingStore && (
          <Modal
            errorIcon
            title="Thay đổi cửa hàng"
            message="Bạn có muốn chuyển qua cửa hàng khác?"
            onClose={this.handleChangeStore(false)}
            onAgree={this.handleConfirmChangingStore}
          />
        )}
        {!isOpenShopDetail &&
          !!gettingShopInfo && (
            <>
              <div className={s.shopInfo}>
                <div className={s.shopIcon}>
                  <Store size={72} hexColor={gray} />
                </div>
                <div className={s.shopDetail}>
                  <div className={s.title}>
                    {formatStringLength(shopName, 20)}
                  </div>
                  <div className={s.detailItem}>
                    <PhoneIphone size={20} hexColor={gray} />
                    <span>&nbsp;{phoneNumber}</span>
                    <span>&nbsp;-&nbsp;</span>
                    <Store size={20} hexColor={gray} />
                    <span>&nbsp;{managerPhone}</span>
                  </div>
                  <div className={s.detailItem}>
                    <Place size={20} hexColor={gray} />
                    <span>&nbsp;{formatStringLength(shopAddress, 69)}</span>
                  </div>
                </div>
                <div
                  className={s.viewDetail}
                  onClick={this.handleShowShopDetail(true)}
                >
                  <ArrowForward size={18} hexColor={gray} />
                </div>
              </div>
              {shopCount && (
                <div className={s.changeStore}>
                  <Button
                    label={CHANGE_STORE}
                    onClick={this.handleChangeStore(true)}
                    variant="text"
                    gutter
                    small
                  />
                </div>
              )}
              {this.createMenu()}
            </>
          )}
      </>
    );
  }
}

const enhancers = [
  connect(
    shopsProps.mapStateToProps,
    shopsProps.mapDispatchToProps,
  ),
  withStyles(s),
];
export default compose(...enhancers)(Shop);

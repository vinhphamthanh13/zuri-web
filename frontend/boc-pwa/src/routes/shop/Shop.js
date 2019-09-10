import React from 'react';
import { func, string } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { connect } from 'react-redux';
import { compose } from 'redux';
import uuidv1 from 'uuid/v1';
import { chunk, get } from 'lodash';
import { SHOP } from 'constants/shop';
import { gray } from 'constants/colors';
import { Store, PhoneIphone, Place, ArrowForward } from 'constants/svg';
import { formatStringLength } from 'utils/string';
import ShopDetail from './components/ShopDetail';
import { shopsProps } from './commonProps';
import s from './Shop.css';

class Shop extends React.Component {
  static propTypes = {
    dispatchGettingStoreInfo: func.isRequired,
    accessToken: string,
  };

  static defaultProps = {
    accessToken: '',
  };

  state = {
    isOpenShopDetail: false,
    selectedShopId: null,
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
    dispatchGettingStoreInfo(selectedShopId, accessToken);
  }

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

  render() {
    const { isOpenShopDetail, gettingShopInfo } = this.state;
    const shopName = get(gettingShopInfo, 'cuaHangName');
    const phone = get(gettingShopInfo, 'phone');
    const shopAddress = get(gettingShopInfo, 'address');

    return (
      <>
        {isOpenShopDetail && (
          <ShopDetail
            onClose={this.handleShowShopDetail}
            shopDetail={gettingShopInfo}
          />
        )}
        {!isOpenShopDetail && (
          <>
            <>
              <div className={s.shopInfo}>
                <div className={s.shopIcon}>
                  <Store size={72} hexColor={gray} />
                </div>
                {!!gettingShopInfo && (
                  <div className={s.shopDetail}>
                    <div className={s.title}>
                      {formatStringLength(shopName, 20)}
                    </div>
                    <div className={s.detailItem}>
                      <PhoneIphone size={20} hexColor={gray} />
                      <span>{phone}</span>
                    </div>
                    <div className={s.detailItem}>
                      <Place size={20} hexColor={gray} />
                      <span>{formatStringLength(shopAddress, 69)}</span>
                    </div>
                  </div>
                )}
                <div
                  className={s.viewDetail}
                  onClick={this.handleShowShopDetail(true)}
                >
                  <ArrowForward size={18} hexColor={gray} />
                </div>
              </div>
              {this.createMenu()}
            </>
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

import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { objectOf, number } from 'prop-types';
import uuidv1 from 'uuid/v1';
import { chunk } from 'lodash';
import BocTabs from 'components/BocTabs';
import Header from 'components/Header';
import { SHOP } from 'constants/shop';
import { gray } from 'constants/colors';
import { Store, PhoneIphone, Place, ArrowForward } from 'constants/svg';
import { formatStringLength } from 'utils/string';
import { resolveDimension } from 'utils/browser';
import s from './Shop.css';

const mockDetail = {
  shopName: 'BOCoffee VN ',
  phoneNumber: '0977785897',
  shopAddress: '01 đường Lê Lợi, Phường Phạm Ngũ Lão, Quận 1, TP. Hồ Chí Minh',
};

class Shop extends React.Component {
  static propTypes = {
    layoutDimension: objectOf(number).isRequired,
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
                  <div className={s.icon}>{menu.icon}</div>
                  <div className={s.label}>{menu.name}</div>
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>
    ));

  render() {
    const { layoutDimension } = this.props;
    const { windowWidth, windowHeight } = layoutDimension;
    const height = windowHeight - 90;

    return (
      <div className={s.container}>
        <Header title="Cửa hàng của tôi" gutter />
        <div
          className={s.content}
          style={resolveDimension(windowWidth, height)}
        >
          <div className={s.shopInfo}>
            <div className={s.shopIcon}>
              <Store size={72} hexColor={gray} />
            </div>
            <div className={s.shopDetail}>
              <div className={s.title}>
                {formatStringLength(mockDetail.shopName, 20)}
              </div>
              <div className={s.detailItem}>
                <PhoneIphone size={20} hexColor={gray} />
                <span>{mockDetail.phoneNumber}</span>
              </div>
              <div className={s.detailItem}>
                <Place size={20} hexColor={gray} />
                <span>{formatStringLength(mockDetail.shopAddress, 69)}</span>
              </div>
            </div>
            <div className={s.viewDetail}>
              <ArrowForward size={18} hexColor={gray} />
            </div>
          </div>
          {this.createMenu()}
        </div>
        <BocTabs activeIndex={3} />
      </div>
    );
  }
}

export default withStyles(s)(Shop);

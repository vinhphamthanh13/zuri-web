import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import uuidv1 from 'uuid/v1';
import { chunk } from 'lodash';
import BocTabs from 'components/BocTabs';
import Header from 'components/Header';
import { SHOP } from 'constants/shop';
import s from './Shop.css';

class Shop extends React.Component {
  createMenu = () =>
    chunk(SHOP.MENU, 2).map(row => (
      <div className={s.row} key={uuidv1()}>
        {row.map(menu => (
          <div className={s.item} key={uuidv1()} onClick={menu.action}>
            <div className={s.icon}>{menu.icon}</div>
            <div>{menu.name}</div>
          </div>
        ))}
      </div>
    ));

  render() {
    return (
      <div className={s.container}>
        <Header title="Cửa hàng của tôi" gutter />
        {this.createMenu()}
        <BocTabs activeIndex={3} />
      </div>
    );
  }
}

export default withStyles(s)(Shop);

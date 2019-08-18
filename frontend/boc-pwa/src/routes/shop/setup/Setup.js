import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import uuidv1 from 'uuid/v1';
import Header from 'components/Header';
import { SHOP } from 'constants/shop';
import { brand02 } from 'constants/colors';
import { goBack } from 'utils/browser';
import { ArrowForward } from 'constants/svg';
import s from './Setup.css';

class Setup extends React.Component {
  createMenu = () => (
    <div className={s.menu}>
      {SHOP.SETUP.STORE.MENU.map(menu => (
        <div className={s.wrappedItem} key={uuidv1()} onClick={menu.action}>
          <div className={s.item}>
            <div className={s.icon}>{menu.ICON}</div>
            <div>{menu.NAME}</div>
            <div className={s.arrow}>
              {<ArrowForward size={18} hexColor={brand02} />}
            </div>
          </div>
        </div>
      ))}
    </div>
  );

  render() {
    return (
      <div className={s.container}>
        <Header
          title="Thiết lập cửa hàng"
          gutter
          iconLeft
          onClickLeft={goBack}
        />
        {this.createMenu()}
      </div>
    );
  }
}

export default withStyles(s)(Setup);

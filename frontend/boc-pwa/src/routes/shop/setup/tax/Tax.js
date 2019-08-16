import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import uuidv1 from 'uuid/v1';
import Header from 'components/Header';
import { goBack } from 'utils/browser';
import { SHOP } from 'constants/shop';
import { triad05, gray } from 'constants/colors';
import { ToggleOn, ToggleOff, NotificationImportant } from 'constants/svg';
import RegisterTax from './components/RegisterTax';
import s from './Tax.css';

class Tax extends React.Component {
  state = {
    isTaxToggled: false,
  };

  handleTaxToggled = () => {
    this.setState(oldState => ({
      isTaxToggled: !oldState.isTaxToggled,
    }));
  };

  createMenu = () => {
    const { isTaxToggled } = this.state;

    return (
      <div className={s.menu}>
        {SHOP.SETUP.STORE.TAX.map(menu => (
          <div className={s.wrappedItem} key={uuidv1()}>
            <div className={s.item}>
              <div className={s.icon}>{menu.icon}</div>
              <div>{menu.name}</div>
              <div className={s.arrow} onClick={this.handleTaxToggled}>
                {isTaxToggled ? (
                  <ToggleOn size={38} hexColor={triad05} />
                ) : (
                  <ToggleOff size={38} hexColor={gray} />
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  };

  render() {
    const { isTaxToggled } = this.state;
    return (
      <div className={s.container}>
        <Header title="Thiết lập thuế" gutter iconLeft onClickLeft={goBack} />
        {this.createMenu()}
        {!isTaxToggled ? (
          <div className={s.legend}>
            <div className={s.legendIcon}>
              <NotificationImportant size={20} hexColor={gray} />
            </div>
            <div>Bật tính năng đăng ký thuế để cài đặt thuế.</div>
          </div>
        ) : (
          <RegisterTax />
        )}
      </div>
    );
  }
}

export default withStyles(s)(Tax);

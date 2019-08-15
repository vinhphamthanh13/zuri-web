import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import uuidv1 from 'uuid/v1';
import Header from 'components/Header';
import { SHOP } from 'constants/shop';
import RegisterTax from './components/RegisterTax';
import { brand02, gray } from 'constants/colors';
import { ArrowForward, ArrowBack, List } from 'constants/svg';
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
        {SHOP.TAX.map(menu => (
          <div className={s.wrappedItem} key={uuidv1()}>
            <div className={s.item}>
              <div className={s.icon}>{menu.icon}</div>
              <div>{menu.name}</div>
              <div className={s.arrow} onClick={this.handleTaxToggled}>
                {isTaxToggled ? (
                  <ArrowForward size={18} hexColor={brand02} />
                ) : (
                  <ArrowBack size={18} hexColor={gray} />
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
        <Header title="Thiết lập thuế" gutter />
        {this.createMenu()}
        {!isTaxToggled ? (
          <div className={s.legend}>
            <div className={s.legendIcon}>
              <List size={18} hexColor={gray} />
            </div>
            <div>Bật tính năng thuế để cài đặt thuế.</div>
          </div>
        ) : (
          <RegisterTax />
        )}
      </div>
    );
  }
}

export default withStyles(s)(Tax);

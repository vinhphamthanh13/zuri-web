import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import uuidv1 from 'uuid/v1';
import Header from 'components/Header';
import Button from 'components/Button';
import { goBack } from 'utils/browser';
import { SHOP } from 'constants/shop';
import { triad05, gray } from 'constants/colors';
import { ToggleOn, ToggleOff, NotificationImportant } from 'constants/svg';
// import RegisterTax from './components/RegisterTax';
import s from './Goods.css';

class Goods extends React.Component {
  state = {
    isTaxToggled: false,
  };

  handleTaxToggled = () => {
    this.setState(oldState => ({
      isTaxToggled: !oldState.isTaxToggled,
    }));
  };

  render() {
    return (
      <div className={s.container}>
        <Header title="Tất cả mặt hàng" gutter iconLeft onClickLeft={goBack} />
        {this.createMenu()}
        <Button>Tạo mặt hàng</Button>
      </div>
    );
  }
}

export default withStyles(s)(Goods);

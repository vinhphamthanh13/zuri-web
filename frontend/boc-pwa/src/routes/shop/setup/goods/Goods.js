import React from 'react';
import { number } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { compose } from 'redux';
// import uuidv1 from 'uuid/v1';
import windowSize from 'react-window-size';
import { LAYOUT } from 'constants/common';
import { resolveDimension, goBack } from 'utils/browser';
import Header from 'components/Header';
import Button from 'components/Button';
import Input from 'components/Input';
// import { SHOP } from 'constants/shop';
// import { triad05, gray } from 'constants/colors';
// import { ToggleOn, ToggleOff, NotificationImportant } from 'constants/svg';
// import RegisterTax from './components/RegisterTax';
import s from './Goods.css';

class Goods extends React.Component {
  static propTypes = {
    windowWidth: number.isRequired,
    windowHeight: number.isRequired,
  };

  state = {
    isAddGoods: false,
    searchGood: '',
  };

  handleSearch = event => {
    const { name, value } = event.target;
    event.preventDefault();
    this.setState({
      [name]: value,
    });
  };

  handleAddGoods = () => {
    this.setState({ isAddGoods: true });
  };

  render() {
    const { searchGood } = this.state;

    return (
      <div className={s.container}>
        <div className={s.search}>
          <Input
            name="searchGood"
            onChange={this.handleSearch}
            value={searchGood}
            gutter
            placeholder="Tìm kiếm mặt hàng / giảm giá"
            search
          />
        </div>
        <div>Mat hang tuy chinh</div>
        <div className={s.goodsCta}>
          <Button
            label="Thêm / Chỉnh sửa danh mục"
            onClick={this.handleAddGoods}
          />
        </div>
      </div>
    );
  }
}

const enhancers = [withStyles(s), windowSize];
export default compose(...enhancers)(Goods);

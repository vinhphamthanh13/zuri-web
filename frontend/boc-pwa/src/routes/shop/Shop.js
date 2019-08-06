import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import BocTabs from 'components/BocTabs';
import s from './Shop.css';

class Shop extends React.Component {
  render() {
    return (
      <div className={s.container}>
        <h1>Cửa hàng của bạn!</h1>
        <BocTabs activeIndex={3} />
      </div>
    );
  }
}

export default withStyles(s)(Shop);

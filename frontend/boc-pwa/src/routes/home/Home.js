import React from 'react';
import moment from 'moment';
import Header from 'components/Header';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import BocTabs from 'components/BocTabs';
import { blockNavigation } from 'utils/browser';
import s from './Home.css';

class Home extends React.Component {
  componentDidMount() {
    this.unblockNavigation = blockNavigation();
  }

  componentWillUnmount() {
    this.unblockNavigation();
  }

  unblockNavigation = null;

  render() {
    return (
      <div className={s.container}>
        <Header title="Tổng quan" gutter />
        <div className={s.invoice}>
          <div className={s.top}>
            <div className={s.header}>Hóa đơn hiện tại</div>
            <div className={s.date}>{moment().format('DD/MM/YYYY')}</div>
          </div>
          <div className={s.middle}>
            <span>12,680,000đ</span>
            <span className={s.label}>Tổng phải thu</span>
          </div>
          <div className={s.bottom}>
            <div className={s.cabinet}>
              <div>3</div>
              <div className={s.label}>Dùng tại bàn</div>
            </div>
            <div className={s.cabinet}>
              <div>2</div>
              <div className={s.label}>Mang đi</div>
            </div>
          </div>
        </div>
        <hr />
        <div className={s.invoice}>
          <div className={s.top}>
            <div className={s.header}>Doanh thu tổng quan</div>
            <div className={s.date}>
              Từ {moment().format('DD/MM/YYYY')} 00:00
            </div>
          </div>
          <div className={s.middle}>
            <span>32,718,000đ</span>
            <span className={s.label}>Tổng số tiền thu được</span>
          </div>
          <div className={s.bottom}>
            <div className={s.cabinet}>
              <div>20</div>
              <div className={s.label}>Số lượng giao dịch</div>
            </div>
            <div className={s.cabinet}>
              <div>1,635,900đ</div>
              <div className={s.label}>Giá trị/Giao dịch</div>
            </div>
          </div>
        </div>
        <hr />
        <div className={s.invoice}>
          <div className={s.top}>
            <div className={s.header}>Doanh thu tổng quan</div>
            <div className={s.date}>7 ngày gần nhất</div>
          </div>
          <div className={s.rowTotal}>
            <div className={s.cabinet}>
              <div className={s.statistic}>
                <div>29.17%</div>
                <div>5.760.000đ</div>
              </div>
              <div className={s.label}>Đồng quê tươi sống</div>
            </div>
            <div className={s.cabinet}>
              <div className={s.statistic}>
                <div>26.87%</div>
                <div>5.307.000đ</div>
              </div>
              <div className={s.label}>Bia + Nước Ngọt + Thuốc lá</div>
            </div>
            <div className={s.cabinet}>
              <div className={s.statistic}>
                <div>13.77%</div>
                <div>2.720.000đ</div>
              </div>
              <div className={s.label}>Hải sản tươi sống BOC</div>
            </div>
          </div>
        </div>
        <hr />
        <div className={s.invoice}>
          <div className={s.top}>
            <div className={s.header}>Mặt hàng bán chạy</div>
            <div className={s.date}>7 ngày gần nhất</div>
          </div>
        </div>
        <BocTabs activeIndex={0} />
      </div>
    );
  }
}

export default withStyles(s)(Home);

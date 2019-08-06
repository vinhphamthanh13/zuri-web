import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import BocTabs from 'components/BocTabs';

import s from './Report.css';

class Report extends React.Component {
  render() {
    const { params } = this.props;
    console.log('paramas', this.props);
    return (
      <div className={s.container}>
        <h1>Báo cáo thu nhập</h1>
        <BocTabs activeIndex={1} />
      </div>
    );
  }
}

export default withStyles(s)(Report);

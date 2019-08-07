import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Header from 'components/Header';
import BocTabs from 'components/BocTabs';

import s from './Report.css';

class Report extends React.Component {
  render() {
    return (
      <div className={s.container}>
        <Header title="Báo cáo" />
        <BocTabs activeIndex={1} />
      </div>
    );
  }
}

export default withStyles(s)(Report);

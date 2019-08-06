import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import BocTabs from 'components/BocTabs';

import s from './Activity.css';

class Activity extends React.Component {
  render() {
    return (
      <div className={s.container}>
        <h1>Hoạt động của Quán</h1>
        <BocTabs activeIndex={2} />
      </div>
    );
  }
}

export default withStyles(s)(Activity);

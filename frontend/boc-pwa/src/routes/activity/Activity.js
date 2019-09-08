import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import s from './Activity.css';

class Activity extends React.Component {
  render() {
    return <div className={s.container}>Hoạt động của cửa hàng</div>;
  }
}

export default withStyles(s)(Activity);

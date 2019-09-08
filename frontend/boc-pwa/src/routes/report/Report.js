import React from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import s from './Report.css';

class Report extends React.Component {
  render() {
    return <div className={s.container} />;
  }
}

export default withStyles(s)(Report);

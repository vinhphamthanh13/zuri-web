import React, { Component } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { EMPTY_ITEM } from 'constants/common';
import BocLogo from 'assets/images/boc_greeting.png';
import s from './Empty.css';

class Empty extends Component {
  static propTypes = {};

  static defaultProps = {};

  state = {};

  render() {
    return (
      <div className={s.emptyItem}>
        <img src={BocLogo} alt="BOCVN" className={s.greeting} />
        <div className={s.info}>{EMPTY_ITEM}</div>
      </div>
    );
  }
}

export default withStyles(s)(Empty);

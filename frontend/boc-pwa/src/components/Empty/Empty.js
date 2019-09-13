import React, { Component } from 'react';
import { string } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { EMPTY_ITEM } from 'constants/common';
import BocLogo from 'assets/images/boc_greeting.png';
import s from './Empty.css';

class Empty extends Component {
  static propTypes = {
    message: string,
  };

  static defaultProps = {
    message: EMPTY_ITEM,
  };

  state = {};

  render() {
    const { message } = this.props;

    return (
      <div className={s.emptyItem}>
        <img src={BocLogo} alt="BOCVN" className={s.greeting} />
        <div className={s.info}>{message}</div>
      </div>
    );
  }
}

export default withStyles(s)(Empty);

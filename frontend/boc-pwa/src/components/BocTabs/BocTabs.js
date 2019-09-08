import React, { Component } from 'react';
import { number, func } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { noop } from 'lodash';
import uuidv1 from 'uuid/v1';
import { TABS, TABICONS } from 'constants/common';
import { brand01, brand04 } from 'constants/colors';
import { navigateTo } from 'utils/browser';
import s from './BocTabs.css';

class BocTabs extends Component {
  static propTypes = {
    activeIndex: number.isRequired,
    unblockNavigation: func,
  };

  static defaultProps = {
    unblockNavigation: noop,
  };

  handleTabClick = url => () => {
    const { unblockNavigation } = this.props;
    unblockNavigation();
    navigateTo(url);
  };

  render() {
    const { activeIndex } = this.props;
    return (
      <div className={s.toolBar}>
        {Object.keys(TABS).map((tab, index) => {
          const [activeStyle, iconSize, iconColor] =
            index === activeIndex
              ? [`${s.tab} ${s.activeTab}`, 28, brand04]
              : [s.tab, 24, brand01];
          const Icon = TABICONS[index];
          return (
            <div
              key={uuidv1()}
              className={activeStyle}
              onClick={this.handleTabClick(TABS[tab].URL)}
            >
              <Icon hexColor={iconColor} size={iconSize} />
              <div>{TABS[tab].NAME}</div>
            </div>
          );
        })}
      </div>
    );
  }
}

export default withStyles(s)(BocTabs);

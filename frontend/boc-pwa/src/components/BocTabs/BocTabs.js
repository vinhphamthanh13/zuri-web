import { BubbleChart, FilterVintage, Reorder, ShowChart } from 'constants/svg';
import React, { Component } from 'react';
import { number } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import uuidv1 from 'uuid/v1';
import { TABS } from 'constants/common';
import { brand01, brand03 } from 'constants/colors';
import history from '../../history';
import s from './BocTabs.css';

const TabIcons = [Reorder, ShowChart, BubbleChart, FilterVintage];

class BocTabs extends Component {
  static propTypes = {
    activeIndex: number.isRequired,
  };

  handleTabClick = url => () => {
    history.push(url);
  };

  render() {
    const { activeIndex } = this.props;
    return (
      <div className={s.toolBar}>
        {Object.keys(TABS).map((tab, index) => {
          const [activeStyle, iconSize, iconColor] =
            index === activeIndex
              ? [`${s.tab} ${s.activeTab}`, 28, brand03]
              : [s.tab, 24, brand01];
          const Icon = TabIcons[index];
          return (
            <div
              key={uuidv1()}
              className={activeStyle}
              onClick={this.handleTabClick(TABS[tab].url)}
            >
              <Icon hexColor={iconColor} size={iconSize} />
              <div>{TABS[tab].name}</div>
            </div>
          );
        })}
      </div>
    );
  }
}

export default withStyles(s)(BocTabs);

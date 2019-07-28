import React, { Component } from 'react';
import { string, func } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { noop } from 'lodash';
import s from './Header.css';

class Header extends Component {
  static propTypes = {
    title: string.isRequired,
    iconName: string,
    onClick: func,
  };

  static defaultProps = {
    onClick: noop,
    iconName: '',
  };

  render() {
    const { title, onClick, iconName } = this.props;

    return (
      <div className={s.container}>
        {title}
        {iconName && (
          <button className={s.button} onClick={onClick}>
            <i className="material-icons">{iconName}</i>
          </button>
        )}
      </div>
    );
  }
}

export default withStyles(s)(Header);

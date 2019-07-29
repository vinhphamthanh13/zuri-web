import React, { Component } from 'react';
import { string, func, bool } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { noop } from 'lodash';
import s from './Header.css';

class Header extends Component {
  static propTypes = {
    title: string.isRequired,
    iconName: string,
    onClick: func,
    gutter: bool,
  };

  static defaultProps = {
    onClick: noop,
    iconName: '',
    gutter: false,
  };

  render() {
    const { title, onClick, iconName, gutter } = this.props;
    const headerStyle = gutter ? `${s.container} ${s.gutter}` : s.container;
    return (
      <div className={headerStyle}>
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

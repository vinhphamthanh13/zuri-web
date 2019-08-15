import React, { Component } from 'react';
import { string, func, bool } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { noop } from 'lodash';
import s from './Header.css';

class Header extends Component {
  static propTypes = {
    title: string.isRequired,
    icon: string,
    iconLeft: string,
    onClick: func,
    onClickLeft: func,
    gutter: bool,
  };

  static defaultProps = {
    onClick: noop,
    onClickLeft: noop,
    icon: '',
    iconLeft: '',
    gutter: false,
  };

  render() {
    const { title, onClick, onClickLeft, icon, iconLeft, gutter } = this.props;
    const headerStyle = gutter ? `${s.container} ${s.gutter}` : s.container;
    return (
      <div className={headerStyle}>
        {iconLeft && (
          <button className={s.button} onClick={onClickLeft}>
            <i className="material-icons">{iconLeft}</i>
          </button>
        )}
        {title}
        {icon && (
          <button className={s.button} onClick={onClick}>
            <i className="material-icons">{icon}</i>
          </button>
        )}
      </div>
    );
  }
}

export default withStyles(s)(Header);

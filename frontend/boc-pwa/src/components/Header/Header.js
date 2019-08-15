import React, { Component } from 'react';
import { string, func, bool } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { noop } from 'lodash';
import { ArrowBack, ArrowForward } from 'constants/svg';
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
          <div className={`${s.button} ${s.left}`} onClick={onClickLeft}>
            <ArrowBack />
          </div>
        )}
        {title}
        {icon && (
          <div className={`${s.button} ${s.right}`} onClick={onClick}>
            <ArrowForward />
          </div>
        )}
      </div>
    );
  }
}

export default withStyles(s)(Header);

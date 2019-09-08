import React from 'react';
import { node, number, objectOf, any, bool } from 'prop-types';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { isIE, isEdge } from 'react-device-detect';
import windowSize from 'react-window-size';
import Header from 'components/Header';
import { LAYOUT } from 'constants/common';
import { resolveDimension, resolveContentHOffset } from 'utils/browser';
import logo from 'assets/images/welcome_boc.png';
import brand from 'assets/images/boc_greeting.png';
import s from './Layout.css';

class Layout extends React.Component {
  static propTypes = {
    children: node.isRequired,
    windowWidth: number.isRequired,
    windowHeight: number.isRequired,
    headerProps: objectOf(any),
    isTab: bool,
    headerOff: bool,
  };

  static defaultProps = {
    headerProps: {},
    isTab: false,
    headerOff: false,
  };

  render() {
    const {
      windowWidth,
      windowHeight,
      headerProps,
      headerProps: { gutter },
      isTab,
      headerOff,
    } = this.props;
    const dimension = resolveDimension(windowWidth, windowHeight);
    const contentOffset = resolveContentHOffset(gutter, isTab);
    const maxContentWidth =
      windowWidth > LAYOUT.MAX_WIDTH ? LAYOUT.MAX_WIDTH : windowWidth;
    const maxContentHeight = windowHeight - contentOffset;
    const contentProps = {
      className: s.content,
      style: resolveDimension(maxContentWidth, maxContentHeight),
    };

    return isIE || isEdge || !resolveDimension(windowWidth, windowHeight) ? (
      <div className={s.unSupport}>
        <div className={s.logo}>
          <img src={logo} alt="BOC VN" width="100%" />
        </div>
        <p>
          BOC VN chỉ hỗ trợ thiết bị có kích thước màn hình tương đối rộng để
          hiển thị thông tin tốt nhất.
        </p>
        <p>BOC VN chạy tốt nhất trên trình duyệt Chrome!</p>
        <div className={s.copyRight}>
          <span>&copy;2020</span>
          <img src={brand} alt="Copy Right BOC" width="70%" />
        </div>
      </div>
    ) : (
      <div style={dimension} className={s.layout}>
        {!headerOff && <Header {...headerProps} />}
        <div {...contentProps}>{this.props.children}</div>
      </div>
    );
  }
}

const enhancers = [withStyles(s), windowSize];

export default compose(...enhancers)(Layout);

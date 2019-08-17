import React from 'react';
import { node, number, func } from 'prop-types';
import { compose } from 'redux';
import { connect } from 'react-redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { isIE, isEdge } from 'react-device-detect';
import windowSize from 'react-window-size';
import logo from 'assets/images/welcome_boc.png';
import brand from 'assets/images/boc_greeting.png';
import { resolveDimension } from 'utils/browser';
import { setLayoutDimension } from 'actions/common';
import s from './Layout.css';

class Layout extends React.Component {
  static propTypes = {
    children: node.isRequired,
    windowWidth: number.isRequired,
    windowHeight: number.isRequired,
    setViewDimension: func.isRequired,
  };

  state = {
    windowWidth: null,
    windowHeight: null,
  };

  static getDerivedStateFromProps(props, state) {
    const { windowWidth, windowHeight } = props;
    const {
      windowWidth: cachedWindowWidth,
      windowHeight: cachedWindowHeight,
    } = state;
    if (
      windowWidth !== cachedWindowWidth ||
      windowHeight !== cachedWindowHeight
    ) {
      return {
        windowWidth,
        windowHeight,
      };
    }

    return null;
  }

  componentDidMount() {
    const { windowWidth, windowHeight, setViewDimension } = this.props;
    setViewDimension({ windowWidth, windowHeight });
  }

  componentDidUpdate(prevProps) {
    const { windowWidth, windowHeight } = prevProps;
    const { setViewDimension } = this.props;
    const {
      windowWidth: cachedWindowWidth,
      windowHeight: cachedWindowHeight,
    } = this.state;

    if (
      windowWidth !== cachedWindowWidth ||
      windowHeight !== cachedWindowHeight
    ) {
      setViewDimension({ windowWidth, windowHeight });
    }
  }

  render() {
    const { windowWidth, windowHeight } = this.props;
    const dimension = resolveDimension(windowWidth, windowHeight);
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
        {this.props.children}
      </div>
    );
  }
}

const enhancers = [
  withStyles(s),
  windowSize,
  connect(
    null,
    dispatch => ({
      setViewDimension(dimension) {
        dispatch(setLayoutDimension(dimension));
      },
    }),
  ),
];

export default compose(...enhancers)(Layout);

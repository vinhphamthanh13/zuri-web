import React, { Component } from 'react';
import { bool, string } from 'prop-types';
import classnames from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';

import s from './FullLoading.css';

/**
 * Input is Snipper component like (Bubble, Cycle)
 * Return FullLoading Component with Absolute position
 * (Maybe Full page or Full Component)
 * @param {Component} InnerComponent
 * @return {Component} Loading Component
 */
function FactoryFullLoading(InnerComponent) {
  class FullLoading extends Component {
    static propTypes = {
      fullPage: bool,
      className: string,
    };

    static defaultProps = {
      fullPage: false,
      className: '',
    };

    render() {
      const { fullPage, className } = this.props;

      const wrapperStyle = classnames(
        s.wrapper,
        fullPage && s.wrapperFullPage,
        className,
      );

      return (
        <div className={wrapperStyle}>
          <InnerComponent />
        </div>
      );
    }
  }

  return withStyles(s)(FullLoading);
}

export default FactoryFullLoading;

/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React, { Component } from 'react';
import { noop } from 'lodash';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import FontIcon from '../FontIcon';
import s from './IconButton.css';

class IconButton extends Component {
  static propTypes = {
    children: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
    className: PropTypes.string,
    disabled: PropTypes.bool,
    href: PropTypes.string,
    icon: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
    inverse: PropTypes.bool,
    neutral: PropTypes.bool,
    onMouseLeave: PropTypes.func,
    onMouseUp: PropTypes.func,
    onClick: PropTypes.func,
    primary: PropTypes.bool,
    smallIcon: PropTypes.bool,
  };

  static defaultProps = {
    children: null,
    disabled: false,
    href: null,
    icon: null,
    inverse: false,
    onMouseLeave: noop,
    onMouseUp: noop,
    onClick: noop,
    className: '',
    neutral: true,
    primary: false,
    smallIcon: false,
  };

  handleMouseUp = event => {
    this.buttonNode.blur();
    if (this.props.onMouseUp) {
      this.props.onMouseUp(event);
    }
  };

  handleMouseLeave = event => {
    this.buttonNode.blur();
    if (this.props.onMouseLeave) {
      this.props.onMouseLeave(event);
    }
  };

  buttonRef = node => {
    this.buttonNode = node;
  };

  render() {
    const {
      children,
      className,
      href,
      icon,
      inverse,
      neutral,
      primary,
      smallIcon,
      ...others
    } = this.props;
    const element = href ? 'a' : 'button';
    const classes = classnames(
      s.toggle,
      neutral && (primary ? s.primary : s.neutral),
      inverse && s.inverse,
      className,
      { [s.smallIcon]: smallIcon },
    );
    const iconClass = classnames(s.icon, { [s.smallIcon]: smallIcon });

    const props = {
      ...others,
      href,
      ref: this.buttonRef,
      className: classes,
      disabled: this.props.disabled,
      onMouseUp: this.handleMouseUp,
      onMouseLeave: this.handleMouseLeave,
    };

    return React.createElement(
      element,
      props,
      icon && <FontIcon className={iconClass} value={icon} />,
      children,
    );
  }
}

export default withStyles(s)(IconButton);

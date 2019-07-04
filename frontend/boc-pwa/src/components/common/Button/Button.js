/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Button.css';

class Button extends Component {
  static propTypes = {
    onClick: PropTypes.func.isRequired,

    // Disables the button
    disabled: PropTypes.bool,

    // If `link` is set, then the button is gonna be an <a/> tag.
    link: PropTypes.oneOfType([PropTypes.string, PropTypes.bool]),

    // Custom React component for the button.
    component: PropTypes.element,

    // HTML `title` attribute
    title: PropTypes.string,

    // Set to `true` to stretch the button to full width
    stretch: PropTypes.bool,

    // CSS class name
    className: PropTypes.string,

    children: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
  };

  static defaultProps = {
    disabled: false,
    stretch: false,
    link: false,
    component: null,
    children: null,
    title: '',
    className: '',
  };

  storeInstance = ref => {
    this.button = ref;
  };

  focus = () => this.button.focus();

  linkOnClick = event => {
    const { disabled, onClick } = this.props;

    // Only handle left mouse button clicks
    // ignoring those ones with a modifier key pressed.
    if (
      event.button !== 0 ||
      event.shiftKey ||
      event.altKey ||
      event.ctrlKey ||
      event.metaKey
    ) {
      return;
    }

    if (disabled) {
      return;
    }

    if (onClick) {
      event.preventDefault();
    }

    this.buttonOnClick();
  };

  buttonOnClick = () => {
    const { onClick } = this.props;
    onClick && onClick();
  };

  render() {
    const {
      component,
      link,
      title,
      disabled,
      onClick,
      stretch,
      className,
      children,
      ...rest
    } = this.props;

    const properties = {
      ...rest,
      ref: this.storeInstance,
      title,
      className: classNames(
        s.buttonReset,
        s.button,
        disabled && s.buttonDisabled,
        stretch && s.buttonStretch,
        link && s.buttonResetLink,
        className,
      ),
    };

    if (link) {
      const LinkComponent = component || 'a';

      return (
        <LinkComponent
          href={component ? undefined : link}
          onClick={this.linkOnClick}
          {...properties}
        >
          {children}
        </LinkComponent>
      );
    }

    return (
      <button
        type="button"
        disabled={disabled}
        onClick={this.buttonOnClick}
        {...properties}
      >
        {children}
      </button>
    );
  }
}

export default withStyles(s)(Button);

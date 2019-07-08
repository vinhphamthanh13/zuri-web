/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
/* eslint-disable import/no-unresolved */
import React from 'react';
import { element, string, oneOfType } from 'prop-types';
import { Manager, Reference, Popper } from 'react-popper';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Popover.css';

class Popover extends React.Component {
  static propTypes = {
    component: element.isRequired,
    children: oneOfType([element, string]),
    placement: string,
    className: string,
  };

  static defaultProps = {
    children: '',
    placement: 'bottom',
    className: '',
  };

  state = {
    isOpen: false,
  };

  onClose = () => this.setState({ isOpen: false });

  togglePopper = () =>
    this.setState(prevState => ({ isOpen: !prevState.isOpen }));

  render() {
    const { component, children, placement: place, className } = this.props;
    const { isOpen } = this.state;
    const { togglePopper, onKeyUp } = this;
    return (
      <Manager>
        <Reference>
          {({ ref }) => (
            <span
              className={className}
              ref={ref}
              onClick={togglePopper}
              onKeyUp={onKeyUp}
              role="button"
              tabIndex={-1}
            >
              {component}
            </span>
          )}
        </Reference>
        {isOpen && (
          <Popper placement={place}>
            {({ ref, style, placement, arrowProps }) => (
              <div
                className={s.popper}
                ref={ref}
                style={style}
                data-placement={placement}
              >
                {children}
                <div
                  className={s.popperArrow}
                  ref={arrowProps.ref}
                  style={arrowProps.style}
                />
              </div>
            )}
          </Popper>
        )}
        {isOpen && (
          <button
            className={s.popoverOverlay}
            onClick={this.onClose}
            tabIndex={-1}
            aria-labelledby="overlay"
            aria-describedby="overlay"
          />
        )}
      </Manager>
    );
  }
}

export default withStyles(s)(Popover);

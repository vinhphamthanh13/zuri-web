/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import PropTypes from 'prop-types';
import { get } from 'lodash';
import RadioButton from './RadioButton';

function isComponentRadio(child) {
  const displayName = get(child, 'type.displayName', '');
  return displayName.indexOf('RadioButton') === -1;
}

class RadioGroup extends React.Component {
  static propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
    disabled: PropTypes.bool,
    name: PropTypes.string,
    onChange: PropTypes.func,
    value: PropTypes.string,
  };

  static defaultProps = {
    children: null,
    className: '',
    disabled: false,
    name: '',
    onChange: () => {},
    value: '',
  };

  handleChange = value => {
    this.props.onChange && this.props.onChange(value, this.props.name);
  };

  renderRadioButtons() {
    return React.Children.map(
      this.props.children,
      (radio, idx) =>
        isComponentRadio(radio) ? (
          radio
        ) : (
          <RadioButton
            {...radio.props}
            checked={radio.props.value === this.props.value}
            disabled={this.props.disabled || radio.props.disabled}
            key={idx}
            label={radio.props.label}
            onChange={this.handleChange}
            value={radio.props.value}
          />
        ),
    );
  }

  render() {
    return (
      <div className={this.props.className}>{this.renderRadioButtons()}</div>
    );
  }
}

export default RadioGroup;

export { RadioButton, RadioGroup };

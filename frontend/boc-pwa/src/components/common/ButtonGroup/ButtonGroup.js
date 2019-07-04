import React, { Component } from 'react';
import {
  arrayOf,
  oneOfType,
  shape,
  node,
  string,
  func,
  bool,
  number,
} from 'prop-types';
import { uniqueId, get } from 'lodash';
import classNames from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './ButtonGroup.css';

class ButtonGroup extends Component {
  static propTypes = {
    className: string,
    buttonClassName: string,
    // This is the value returned when the button is selected
    value: oneOfType([string, number]),
    buttons: arrayOf(
      shape({
        value: oneOfType([string, number]).isRequired,
        content: node,
        title: string,
      }),
    ).isRequired,
    onChange: func.isRequired,
    // If false, exactly one button _must_ be selected
    allowEmpty: bool,
  };

  static defaultProps = {
    className: '',
    buttonClassName: '',
    value: null,
    allowEmpty: true,
  };

  /**
   * Handle when user select option
   *
   * @param  {Event} e Click Button Event
   * @return {Void}
   */
  toggleSelect = e => {
    const newValue = get(e, 'target.value');
    const { value, allowEmpty, onChange } = this.props;

    if (allowEmpty) {
      // Select the new button or unselect if it's already selected
      onChange(value !== newValue ? newValue : null);
    } else {
      onChange(newValue);
    }
  };

  render() {
    const { value, buttons, className, buttonClassName } = this.props;

    const buttonElements = buttons.map((button, i) => (
      <button
        title={button.title}
        type="button"
        id={`${i}`}
        key={`button_${uniqueId()}`}
        className={classNames(
          s.button,
          buttonClassName,
          button.value === value && s.selected,
          button.disabled && s.disabled,
        )}
        value={button.value}
        onClick={this.toggleSelect}
      >
        {button.content || `${button.value}`}
      </button>
    ));

    return (
      <div className={classNames(s.outerStyle, className)}>
        {buttonElements}
      </div>
    );
  }
}

export default withStyles(s)(ButtonGroup);

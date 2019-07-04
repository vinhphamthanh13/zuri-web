import React, { Component } from 'react';
import { func, string, bool } from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import classNames from 'classnames';
import { RadioButton, RadioGroup } from 'homecredit-ui';

import s from './GenderSelect.css';

class GenderSelect extends Component {
  static propTypes = {
    onChange: func.isRequired,
    name: string.isRequired,
    value: string.isRequired,
    label: string.isRequired,
    icon: bool,
    require: bool,
    className: string,
  };

  static defaultProps = {
    icon: false,
    require: false,
    className: '',
  };

  handleChange = value => {
    this.props.onChange && this.props.onChange(this.props.name, value);
  };

  render() {
    const { value, name, label, icon, require, className } = this.props;
    const iconClassName = classNames('material-icons', { [s.icon]: icon });

    return (
      <div className={classNames(s.itemInfo, className)}>
        {icon && <i className={iconClassName}>supervisor_account</i>}
        <span>
          {label}
          {require && <sup>(*)</sup>}
        </span>
        <RadioGroup
          name={name}
          value={value}
          onChange={this.handleChange}
          className={s.genderGroup}
        >
          <RadioButton label="Nam" value="MALE" />
          <RadioButton label="Nữ" value="FEMALE" />
        </RadioGroup>
      </div>
    );
  }
}

export default withStyles(s)(GenderSelect);

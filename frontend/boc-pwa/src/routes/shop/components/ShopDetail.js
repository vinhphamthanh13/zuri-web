import React, { Component } from 'react';
import { func, objectOf, any } from 'prop-types';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { withFormik } from 'formik';
import Input from 'components/Input';
import Button from 'components/Button';
import { SHOP_DETAIL } from 'constants/common';
import s from './ShopDetail.css';

class ShopDetail extends Component {
  static propTypes = {
    onClose: func.isRequired,
    shopDetail: objectOf(any),
  };

  static defaultProps = {
    shopDetail: {},
  };

  state = {
    isEditable: false,
  };

  handleSaveShopDetail = () => {};

  createInfo = () => {
    const { handleChange, errors, values } = this.props;
    return Object.keys(SHOP_DETAIL.CATEGORY).map(menu => (
      <div key={menu} className={s.menu}>
        <div className={s.title}>{SHOP_DETAIL.CATEGORY[menu].NAME}</div>
        <div className={s.items}>
          {SHOP_DETAIL.CATEGORY[menu].MENU.map(input => (
            <Input
              key={input.VALUE}
              type={input.TYPE || 'text'}
              label={input.LABEL}
              name={input.VALUE}
              onChange={handleChange}
              errors={errors}
              value={values[input.VALUE]}
              placeholder={input.PLACEHOLDER}
              gutter={input.GUTTER || false}
            />
          ))}
        </div>
      </div>
    ));
  };

  createCta = () => {};

  render() {
    const { onClose, isValid } = this.props;
    return (
      <div className={s.container}>
        <div className={s.formInfo}>
          {this.createInfo()}
          <Button
            label="Lưu"
            onClick={this.handleSaveShopDetail}
            disabled={!isValid}
          />
        </div>
      </div>
    );
  }
}

export default compose(
  withFormik({
    isInitialValid: true,
    // mapPropsToValues: props => ({
    //
    // }),
  }),
  withStyles(s),
)(ShopDetail);

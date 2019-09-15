import React, { Component } from 'react';
import { func, bool, objectOf, any, string } from 'prop-types';
import { compose } from 'redux';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { withFormik } from 'formik';
import Input from 'components/Input';
import Button from 'components/Button';
import { SHOP_DETAIL, EDIT, CANCEL, SAVE, CLOSE } from 'constants/common';
import { editShop } from 'constants/schemas';
import { triad06, success, gray } from 'constants/colors';
import { Clear, CheckCircleOutline, Create } from 'constants/svg';
import s from './ShopDetail.css';

class ShopDetail extends Component {
  static propTypes = {
    onClose: func.isRequired,
    isValid: bool.isRequired,
    handleSubmit: func.isRequired,
    values: objectOf(any).isRequired,
    errors: objectOf(string).isRequired,
    touched: objectOf(bool).isRequired,
    handleChange: func.isRequired,
    setFieldTouched: func.isRequired,
  };

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {
      isEditable: false,
      initValues: props.values,
    };
  }

  handleEditShopDetail = value => () => {
    this.setState({ isEditable: value });
  };

  handleSaveShopDetail = () => {
    const { handleSubmit, onClose } = this.props;
    onClose(false)();
    handleSubmit();
  };

  createInfo = () => {
    const {
      handleChange,
      errors,
      values,
      touched,
      setFieldTouched,
    } = this.props;

    const { initValues, isEditable } = this.state;
    const resolvedValues = isEditable ? values : initValues;

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
              value={resolvedValues[input.VALUE]}
              placeholder={input.PLACEHOLDER}
              gutter={input.GUTTER || false}
              disabled={!isEditable}
              touched={touched}
              onTouch={setFieldTouched}
            />
          ))}
        </div>
      </div>
    ));
  };

  createCta = () => {
    const { isValid, values } = this.props;
    const { initValues } = this.state;
    const formDusty = JSON.stringify(values) === JSON.stringify(initValues);
    const saveDisabled = !isValid || formDusty;
    const iconSaveColor = !saveDisabled ? success : gray;
    return (
      <div className={s.editCta}>
        <Button
          variant="text"
          onClick={this.handleEditShopDetail(false)}
          label={CANCEL}
        >
          <Clear hexColor={triad06} />
        </Button>
        <Button
          variant="text"
          onClick={this.handleSaveShopDetail}
          label={SAVE}
          disabled={saveDisabled}
        >
          <CheckCircleOutline hexColor={iconSaveColor} />
        </Button>
      </div>
    );
  };

  render() {
    const { onClose } = this.props;
    const { isEditable } = this.state;
    return (
      <div className={s.container}>
        <div className={s.formInfo}>
          {this.createInfo()}
          {!isEditable && (
            <div className={s.editableButton}>
              <Button variant="text" label={CLOSE} onClick={onClose(false)}>
                <Clear hexColor={triad06} />
              </Button>
              <Button
                variant="text"
                label={EDIT}
                onClick={this.handleEditShopDetail(true)}
              >
                <Create hexColor={success} />
              </Button>
            </div>
          )}
          {isEditable && this.createCta()}
        </div>
      </div>
    );
  }
}

const enhancers = [
  withFormik({
    validationSchema: editShop,
    enableReinitialize: true,
    validateOnBlur: true,
    isInitialValid: true,
    mapPropsToValues: props => {
      const {
        shopDetail: {
          address,
          cuaHangName,
          danhMucMatHangType,
          managerEmail,
          managerName,
          managerPhone,
          moHinhKinhDoanhType,
          phone,
        },
      } = props;
      return {
        businessType: moHinhKinhDoanhType,
        categoryType: danhMucMatHangType,
        shopName: cuaHangName,
        managerPhone,
        shopAddress: address,
        userName: managerName,
        phoneNumber: phone,
        userEmail: managerEmail,
      };
    },
    handleSubmit: (
      values,
      {
        props: {
          updatingStore,
          shopDetail: { id, accessToken },
        },
      },
    ) => {
      updatingStore(JSON.stringify({ ...values, id }), accessToken);
    },
  }),
  withStyles(s),
];

export default compose(...enhancers)(ShopDetail);

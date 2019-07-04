/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
import React from 'react';
import { Formik, Form } from 'formik';
import * as yup from 'yup';
import { get } from 'lodash';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import {
  Input,
  Slider,
  Popover,
  Modal,
  DatePicker,
  Dropdown,
  OTPCountdown,
} from 'homecredit-ui';

import s from './Styleguide.css';

const TestingSchema = yup.object().shape({
  email: yup
    .string()
    .email('Invalid email')
    .required('Required'),
});

class Styleguide extends React.Component {
  constructor() {
    super();
    this.state = {
      inputValue: '',
      sliderValue: 10,
      dateValue: null,
    };
  }

  changeInput = e => {
    this.setState({
      inputValue: get(e, 'target.value'),
    });
  };

  changeSlider = value => {
    this.setState({
      sliderValue: value,
    });
  };

  changeDate = value => {
    this.setState({
      dateValue: value,
    });
  };

  render() {
    const { inputValue } = this.state;

    return (
      <div className={s.root}>
        <div className={s.container}>
          <h1>http://www.bocvietnam.com</h1>
        </div>

        <Formik
          initialValues={{
            email: '',
            image: '',
            price: '',
          }}
          validationSchema={TestingSchema}
          onSubmit={() => {
            // same shape as initial values
            // console.log(values);
          }}
        >
          {({ values, touched, errors, handleChange }) => (
            <Form noValidate>
              <Input
                label="General textfield"
                type="email"
                name="email"
                id="input"
                value={values.email}
                icon="face"
                onChange={handleChange}
                touched={touched.email}
                errorMsg={errors.email && touched.email ? errors.email : ''}
              />
              <Input
                label="File input"
                type="file"
                accept="image/*"
                name="image"
                id="input"
                value={values.image}
                icon="face"
                onChange={handleChange}
                touched={touched.image}
                errorMsg={errors.image && touched.image ? errors.image : ''}
              />
              <Input
                label="Number"
                type="number"
                name="price"
                id="input"
                value={values.price}
                icon="face"
                onChange={handleChange}
                touched={touched.price}
                errorMsg={errors.price && touched.price ? errors.price : ''}
              />
              <button type="submit">Submit</button>
            </Form>
          )}
        </Formik>

        <Input
          id="input"
          value={inputValue}
          onChange={this.changeInput}
          label="Input without icon"
        />

        <Input
          id="error"
          value={inputValue}
          icon="face"
          onChange={this.changeInput}
          label="Input with icon"
          errorMsg="This field is require."
        />

        <Slider
          min={5}
          max={80}
          step={5}
          value={this.state.sliderValue}
          onChange={this.changeSlider}
        />

        <DatePicker
          onChange={this.changeDate}
          label="Birthday"
          value={this.state.dateValue}
        />

        <div>
          <Popover className="popover" component={<span>Popover</span>}>
            Popover description
          </Popover>
        </div>

        <Modal toggleButton={<button>Open Modal</button>}>
          <h3>Điêu khoản & điều kiện</h3>
          <p>text...</p>
        </Modal>

        <Modal
          toggleButton={<button>Open Modal with footer</button>}
          footer={
            <div className={s.modalFooter}>
              <h4>Modal Footer</h4>
            </div>
          }
        >
          <h3>Modal with footer</h3>
          <p>text...</p>
        </Modal>

        <br />

        <div>
          <h5 className={s.dropdown}>Khoản vay</h5>
          <Dropdown
            value={1}
            placeholder="Vui lòng chọn khoản vay ..."
            options={[
              { value: 1, label: '12 tháng' },
              { value: 2, label: '24 tháng' },
            ]}
            name="dropdown"
          />
        </div>

        <p>
          <p> Count down with 1:30</p>
          <OTPCountdown className={s.countDown} min={1} sec={10} />
        </p>
      </div>
    );
  }
}

export default withStyles(s)(Styleguide);

/* eslint-env jest */
import React from 'react';
// import { shallow } from 'enzyme';
import renderer from 'react-test-renderer';

import OTPCountDown from '../../OTPCountDown';

describe('OTP Count Down', () => {
  it('should render OTP Count Down with default props', () => {
    const wrapper = renderer.create(<OTPCountDown />);
    const snapshot = wrapper.toJSON();

    expect(snapshot).toMatchSnapshot();
  });
});

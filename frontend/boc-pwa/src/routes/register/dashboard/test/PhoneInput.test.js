import React from 'react';
import renderer from 'react-test-renderer';

import PhoneInput from '../PhoneInput';

it('PhoneInput snapshot', () => {
  const wrapper = renderer.create(<PhoneInput sendOTP={() => {}} />);
  const snapshot = wrapper.toJSON();

  expect(snapshot).toMatchSnapshot();
});

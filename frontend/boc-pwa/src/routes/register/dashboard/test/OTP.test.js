import React from 'react';
import renderer from 'react-test-renderer';

import OTP from '../OTP';

it('OTP snapshot', () => {
  const wrapper = renderer.create(
    <OTP createAt="" unRegister={() => {}} showUnRegister />,
  );
  const snapshot = wrapper.toJSON();

  expect(snapshot).toMatchSnapshot();
});

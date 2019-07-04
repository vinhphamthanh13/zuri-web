import React from 'react';
import renderer from 'react-test-renderer';

import Finished from '../Finished';

it('Finished snapshot', () => {
  const wrapper = renderer.create(
    <Finished createAt="" unRegister={() => {}} showUnRegister />,
  );
  const snapshot = wrapper.toJSON();

  expect(snapshot).toMatchSnapshot();
});

/* eslint-env jest */
import React from 'react';
import renderer from 'react-test-renderer';

import DesktopDesign from '../DesktopDesign';

describe('Desktop Design', () => {
  it('should render Desktop Design', () => {
    const wrapper = renderer.create(
      <DesktopDesign>
        <div />
      </DesktopDesign>,
    );
    const snapshot = wrapper.toJSON();

    expect(snapshot).toMatchSnapshot();
  });
});

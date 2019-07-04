/* eslint-env jest */
import React from 'react';
import renderer from 'react-test-renderer';

import DatePicker from '../../DatePicker';

describe('DatePicker', () => {
  it('should render DatePicker with default props', () => {
    const wrapper = renderer.create(<DatePicker />);
    const snapshot = wrapper.toJSON();

    expect(snapshot).toMatchSnapshot();
  });
});

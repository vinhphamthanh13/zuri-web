/* eslint-env jest */
import React from 'react';
import { shallow } from 'enzyme';
import renderer from 'react-test-renderer';

import CallButton from '../CallButton';

describe('Call Button', () => {
  it('should render call button with default props', () => {
    const wrapper = renderer.create(<CallButton />);
    const snapshot = wrapper.toJSON();

    expect(snapshot).toMatchSnapshot();
  });

  it('should show hotline modal when click button', () => {
    const wrapper = shallow(<CallButton />);

    wrapper.find('.welcome').simulate('click');

    expect(wrapper.state().isShowingHotLine).toBe(true);
  });
});

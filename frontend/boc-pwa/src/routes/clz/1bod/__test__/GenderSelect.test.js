/* eslint-env jest */
import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import { shallow } from 'enzyme';

import App from 'components/App';
import GenderSelect from '../GenderSelect';

const mockStore = configureStore();
const initialState = {
  runtime: {
    availableLocales: ['en-US'],
  },
};

describe('GenderSelect', () => {
  it('renders children correctly', () => {
    const store = mockStore(initialState);
    const props = {
      onChange: jest.fn(),
      name: 'Male',
      value: 'male',
      label: '',
    };

    const wrapper = renderer
      .create(
        <App
          context={{
            insertCss: () => {},
            fetch: () => {},
            pathname: '',
            store,
          }}
        >
          <GenderSelect {...props}>
            <div className="child" />
          </GenderSelect>
        </App>,
      )
      .toJSON();

    expect(wrapper).toMatchSnapshot();
  });

  it('should set values is correctly', () => {
    const props = {
      onChange: jest.fn(),
      name: '',
      value: '',
      label: '',
    };
    const wrapper = shallow(<GenderSelect {...props} />);
    wrapper.instance().handleChange();

    expect(props.onChange).toHaveBeenCalled();
  });
});

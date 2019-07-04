import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

import Loading from '../Loading';

const mockStore = configureStore();
const initialState = {
  saRegister: {
    loading: true,
  },
};

it('Loading snapshot', () => {
  const store = mockStore(initialState);
  const wrapper = renderer.create(
    <Provider store={store}>
      <Loading />
    </Provider>,
  );
  const snapshot = wrapper.toJSON();

  expect(snapshot).toMatchSnapshot();
});

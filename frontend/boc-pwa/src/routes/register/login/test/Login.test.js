import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

import Login from '../Login';

const mockStore = configureStore();
const initialState = {
  saRegister: {
    auth: {
      username: null,
    },
  },
};

it('Login snapshot', () => {
  const store = mockStore(initialState);
  const wrapper = renderer.create(
    <Provider store={store}>
      <Login />
    </Provider>,
  );
  const snapshot = wrapper.toJSON();

  expect(snapshot).toMatchSnapshot();
});

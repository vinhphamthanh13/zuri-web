import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

import HeaderBar from '../HeaderBar';

const mockStore = configureStore();
const initialState = {
  saRegister: {
    auth: {
      username: null,
    },
  },
};

it('HeaderBar snapshot', () => {
  const store = mockStore(initialState);
  const wrapper = renderer.create(
    <Provider store={store}>
      <HeaderBar />
    </Provider>,
  );
  const snapshot = wrapper.toJSON();

  expect(snapshot).toMatchSnapshot();
});

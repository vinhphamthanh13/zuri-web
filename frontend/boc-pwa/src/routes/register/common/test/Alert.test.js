import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

import Alert from '../Alert';

const mockStore = configureStore();
const initialState = {
  saRegister: {
    alert: {
      title: 'Lỗi xãy ra',
      message: 'Something happend!',
    },
  },
};

it('Alert snapshot', () => {
  const store = mockStore(initialState);
  const wrapper = renderer.create(
    <Provider store={store}>
      <Alert />
    </Provider>,
  );
  const snapshot = wrapper.toJSON();

  expect(snapshot).toMatchSnapshot();
});

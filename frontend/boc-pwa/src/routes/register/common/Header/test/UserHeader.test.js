import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

import UserHeader from '../UserHeader';

const mockStore = configureStore();
const initialState = {
  saRegister: {
    auth: {
      username: 'homerselect',
    },
  },
};

it('HeaderBar snapshot', () => {
  const store = mockStore(initialState);
  const wrapper = renderer.create(
    <Provider store={store}>
      <UserHeader logout={() => {}} />
    </Provider>,
  );
  const snapshot = wrapper.toJSON();

  expect(snapshot).toMatchSnapshot();
});

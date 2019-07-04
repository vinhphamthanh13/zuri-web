/* eslint-env jest */
/* eslint-disable padded-blocks, no-unused-expressions */

import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import App from '../../App';
import LayoutMobile from '../LayoutMobile';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);
const initialState = {
  runtime: {
    availableLocales: ['en-US'],
  },
  intl: {
    locale: 'en-US',
  },
};

describe('LayoutMobile', () => {
  test('renders children correctly', () => {
    const store = mockStore(initialState);

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
          <LayoutMobile>
            <div className="child" />
          </LayoutMobile>
        </App>,
      )
      .toJSON();

    expect(wrapper).toMatchSnapshot();
  });

  test('renders mobile layout with fullHeight and fullWidth', () => {
    const store = mockStore(initialState);
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
          <LayoutMobile fullHeight fullWidth>
            <div className="child" />
          </LayoutMobile>
        </App>,
      )
      .toJSON();

    expect(wrapper).toMatchSnapshot();
  });
});

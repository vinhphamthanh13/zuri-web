import configureStore from 'redux-mock-store';

// Setup store
const mockStore = configureStore();
const initialState = {
  runtime: {
    availableLocales: ['en-US'],
  },
};
const store = mockStore(initialState);

export default { store };

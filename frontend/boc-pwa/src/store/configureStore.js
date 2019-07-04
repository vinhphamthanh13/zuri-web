import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension/developmentOnly';
import { persistStore, persistReducer } from 'redux-persist';

import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';

import storage from 'redux-persist/lib/storage';
import { name, version } from '../../package.json';
import rootReducer from '../reducers';
import createLogger from './logger';

export default function configureStore(initialState, helpersConfig) {
  const middleware = [thunk.withExtraArgument(helpersConfig)];

  const persistConfig = {
    key: 'BOCVN',
    storage,
    stateReconciler: autoMergeLevel2,
    blacklist: ['clz'],
  };

  const persistedReducer = persistReducer(persistConfig, rootReducer);

  let enhancer;

  if (__DEV__) {
    middleware.push(createLogger());

    // https://github.com/zalmoxisus/redux-devtools-extension#14-using-in-production
    const composeEnhancers = composeWithDevTools({
      // Options: https://github.com/zalmoxisus/redux-devtools-extension/blob/master/docs/API/Arguments.md#options
      name: `${name}@${version}`,
    });

    // https://redux.js.org/docs/api/applyMiddleware.html
    enhancer = composeEnhancers(applyMiddleware(...middleware));
  } else {
    enhancer = applyMiddleware(...middleware);
  }

  // https://redux.js.org/docs/api/createStore.html
  const store = createStore(persistedReducer, enhancer);

  // Hot reload reducers (requires Webpack or Browserify HMR to be enabled)
  if (__DEV__ && module.hot) {
    module.hot.accept('../reducers', () =>
      // Don't forget to remove `()` if you change reducers back to normal rootReducer.
      // eslint-disable-next-line global-require
      store.replaceReducer(require('../reducers').default()),
    );
  }

  const persistor = persistStore(store);

  return { store, persistor };
}

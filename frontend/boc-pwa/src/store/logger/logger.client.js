import { createLogger as reduxLogger } from 'redux-logger';

export default () =>
  // https://github.com/evgenyrodionov/redux-logger#options
  reduxLogger({
    collapsed: true,
  });

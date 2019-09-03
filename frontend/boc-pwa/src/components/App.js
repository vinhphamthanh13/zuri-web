import React from 'react';
import PropTypes from 'prop-types';
import Error from 'components/Error';
import Loading from 'components/Loading';
import { Provider as ReduxProvider } from 'react-redux';

const ContextType = {
  // Enables critical path CSS rendering
  // https://github.com/BOCVN/isomorphic-style-loader
  insertCss: PropTypes.func.isRequired,
  // Universal HTTP client
  fetch: PropTypes.func.isRequired,
  pathname: PropTypes.string.isRequired,
  query: PropTypes.object,
  // Integrate Redux
  // http://redux.js.org/docs/basics/UsageWithReact.html
  ...ReduxProvider.childContextTypes,
  // ReactIntl
  locale: PropTypes.string,
  store: PropTypes.object.isRequired,
};

class App extends React.PureComponent {
  static propTypes = {
    context: PropTypes.shape(ContextType).isRequired,
    children: PropTypes.element.isRequired,
  };
  static childContextTypes = ContextType;

  getChildContext() {
    return this.props.context;
  }

  render() {
    const { context, children } = this.props;
    const { store } = context;

    return (
      <ReduxProvider store={store}>
        <>
          <Error />
          <Loading />
          {React.Children.only(children)}
        </>
      </ReduxProvider>
    );
  }
}

export default App;

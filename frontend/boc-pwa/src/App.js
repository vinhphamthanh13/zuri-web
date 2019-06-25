import React from 'react';
import Loadable from 'react-loadable';
import logo from './logo.svg';
import './App.css';

const AsyncComponent = Loadable({
  loader: () => import('./components/Loading'),
  loading: () => (<div>this is loadable loading</div>),
});

function App() {
  return (
    <div className="App">
      {/*<header className="App-header">*/}
      {/*  <img src={logo} className="App-logo" alt="logo" />*/}
      {/*  <p>*/}
      {/*    Edit <code>src/App.js</code> and save to reload.*/}
      {/*  </p>*/}
      {/*  <a*/}
      {/*    className="App-link"*/}
      {/*    href="https://reactjs.org"*/}
      {/*    target="_blank"*/}
      {/*    rel="noopener noreferrer"*/}
      {/*  >*/}
      {/*    Learn React*/}
      {/*  </a>*/}
      {/*</header>*/}
      <AsyncComponent />
    </div>
  );
}

export default App;

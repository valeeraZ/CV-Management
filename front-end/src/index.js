import React,{ Suspense } from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './i18n';
import * as serviceWorker from './serviceWorker';
import {
  BrowserRouter,
  Route,
  Switch
} from 'react-router-dom';
import LoginPage from './LoginPage/LoginPage';
import RegisterPage from './RegisterPage/RegisterPage';
import HomePage from './HomePage/HomePage';

ReactDOM.render(
  <React.StrictMode>
    <Suspense fallback="loading">
    <BrowserRouter>
      <Switch>
        <Route exact path ="/" component={HomePage}/>
        <Route path='/login' component={LoginPage} />
        <Route path='/register' component={RegisterPage}/>
      </Switch>
    </BrowserRouter>
    </Suspense>
    
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

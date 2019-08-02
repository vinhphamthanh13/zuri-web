/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

// The top-level (parent) route
const routes = {
  path: '',

  // Keep in mind, routes are evaluated in order
  children: [
    {
      path: '/',
      load: () =>
        import(/* webpackChunkName: 'login' */ './authentication/login'),
    },
    {
      path: '/activation',
      load: () =>
        import(/* webpackChunkName: 'activation' */ './authentication/activation'),
    },
    {
      path: '/verify-code',
      load: () =>
        import(/* webpackChunkName: 'verifyCode' */ './authentication/verifyCode'),
    },
    {
      path: '/register',
      load: () =>
        import(/* webpackChunkName: 'register' */ './authentication/register'),
    },
    {
      path: '/home',
      load: () => import(/* webpackChunkName: 'home' */ './home'),
    },
    {
      path: '/report',
      load: () => import(/* webpackChunkName: 'report' */ './report'),
    },
    {
      path: '/activity',
      load: () => import(/* webpackChunkName: 'activity' */ './activity'),
    },
    {
      path: '/shop',
      load: () => import(/* webpackChunkName: 'shop' */ './shop'),
    },
    // Wildcard routes, e.g. { path: '(.*)', ... } (must go last)
    {
      path: '(.*)',
      load: () => import(/* webpackChunkName: 'not-found' */ './not-found'),
    },
  ],

  async action({ next }) {
    // Execute each child route until one of them return the result
    const route = await next();

    // Provide default values for title, description etc.
    route.title = `${route.title ||
      'Không tìm thấy trang'} - http://www.bocvietnam.com`;
    route.description = route.description || '';

    return route;
  },
};

// The error page is available by permanent url for development mode
if (__DEV__) {
  routes.children.unshift({
    path: '/error',
    action: import('./error').default,
  });
}

export default routes;

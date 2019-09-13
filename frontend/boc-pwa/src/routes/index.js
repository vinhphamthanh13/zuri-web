import { ROUTER_URL } from 'constants/routerUrl';

export const authRoute = ROUTER_URL.AUTH;
export const tabsRoute = ROUTER_URL.TABS;
export const setupRoute = ROUTER_URL.SETUP;

// The top-level (parent) route
const routes = {
  path: '',

  // Keep in mind, routes are evaluated in order
  children: [
    {
      path: `${authRoute.LOGIN}`,
      load: () =>
        import(/* webpackChunkName: 'login' */ './authentication/login'),
    },
    {
      path: `${authRoute.CREATING_STORE}`,
      load: () =>
        import(/* webpackChunkName: 'registerShop' */ './authentication/registerShop'),
    },
    {
      path: `${authRoute.ACTIVATION}`,
      load: () =>
        import(/* webpackChunkName: 'activation' */ './authentication/activation'),
    },
    {
      path: `${authRoute.VERIFYING_OTP}`,
      load: () =>
        import(/* webpackChunkName: 'verifyOTP' */ './authentication/verifyOTP'),
    },
    {
      path: `${authRoute.SHOPS}`,
      load: () =>
        import(/* webpackChunkName: 'shops' */ './authentication/shops'),
    },
    {
      path: `${tabsRoute.HOME}`,
      load: () => import(/* webpackChunkName: 'home' */ './home'),
    },
    {
      path: `${tabsRoute.REPORT}`,
      load: () => import(/* webpackChunkName: 'report' */ './report'),
    },
    {
      path: `${tabsRoute.ACTIVITY}`,
      load: () => import(/* webpackChunkName: 'activity' */ './activity'),
    },
    // Shop Route and Children
    {
      path: `${tabsRoute.SHOP}`,
      load: () => import(/* webpackChunkName: 'shop' */ './shop'),
    },
    {
      path: `${setupRoute.SHOP}`,
      load: () => import(/* webpackChunkName: 'shopSetup' */ './shop/setup'),
    },
    {
      path: `${setupRoute.TAX}`,
      load: () => import(/* webpackChunkName: 'shopTax' */ './shop/setup/tax'),
    },
    {
      path: `${setupRoute.GOODS}`,
      load: () =>
        import(/* webpackChunkName: 'shopGoods' */ './shop/setup/goods'),
    },
    {
      path: `${setupRoute.PRINTING}`,
      load: () =>
        import(/* webpackChunkName: 'printing' */ './shop/setup/printing'),
    },
    // {
    //   path: '/shop/setup/openedOrder',
    //   load: () =>
    //     import(/* webpackChunkName: 'shopSetupOpenedOrder' */ './shop/setup/openOrder'),
    // },
    // {
    //   path: '/shop/setup/printer',
    //   load: () =>
    //     import(/* webpackChunkName: 'shopSetupPrinter' */ './shop/setup/printer'),
    // },
    // {
    //   path: '/shop/setup/shopInfo',
    //   load: () =>
    //     import(/* webpackChunkName: 'shopSetupPrinter' */ './shop/setup/shopInfo'),
    // },
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

/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

/* eslint-disable max-len */

if (process.env.BROWSER) {
  throw new Error(
    'Do not import `config.js` from inside the client-side code.',
  );
}

module.exports = {
  // default locale is the first one
  locales: [
    /* @intl-code-template '${lang}-${COUNTRY}', */
    'vi-VN',
    'cs-CZ',
    'en-US',
    /* @intl-code-template-end */
  ],

  // Node.js app
  port: process.env.PORT || 3002,

  // https://expressjs.com/en/guide/behind-proxies.html
  trustProxy: process.env.TRUST_PROXY || 'loopback',

  sessionSecret: process.env.SESSION_SECRECT || 'Y2x6',

  // API Gateway
  api: {
    // API URL to be used in the client-side code
    clientUrl: process.env.API_CLIENT_URL || '',
    // API URL to be used in the server-side code
    serverUrl:
      process.env.API_SERVER_URL ||
      `http://localhost:${process.env.PORT || 3002}`,
    searchPOSUrl:
      process.env.SEARCH_POS_URL ||
      'http://mobileapp2-uat.homecredit.vn/api/clw/pos',
    sendEmailUrl:
      process.env.SMT_URL || 'http://smtsend.homecredit.vn/SmtSend.svc/email',
    javaAPI: process.env.JAVA_API || 'http://api-dev.homecredit.vn/web-api',
  },

  mobileAPI: {
    url:
      process.env.MOBILE_API ||
      'http://172.24.65.59:8087/admin-portal/api/v1/internal-services',
    apiKey: process.env.MOBILE_API_KEY || '123456',
  },

  masterData: {
    url: process.env.MASTER_DATA_URL || 'http://68.183.188.123:8080/api',
    apikey: process.env.API_KEY || '485cafb9a6804461b3c6e440fe568c6e',
  },

  localDB: {
    mongoConnection:
      process.env.MONGO_DB ||
      'mongodb://app_acl:app_acl@10.19.175.63:27017/acl?authMechanism=DEFAULT',
    trackingMongoConnection:
      process.env.MONGO_TRACKING_DB ||
      'mongodb://app_acl:app_acl@10.19.175.63:27017/acl?authMechanism=DEFAULT',
  },

  // Web analytics
  analytics: {
    // https://analytics.google.com/
    googleTrackingId: process.env.GOOGLE_TRACKING_ID || 'UA-130587617-1',
  },

  threatMetrix: {
    id: process.env.THREAT_METRIX_ORG_ID || 'adr4m5q1',
    scriptLink: 'https://h.online-metrix.net/fp/tags.js',
    embedLink: 'https://h.online-metrix.net/tags',
  },

  // Authentication
  auth: {
    jwt: { secret: process.env.JWT_SECRET || 'http://www.bocvietnam.com' },
    userAuth: {
      jwtSecret:
        process.env.CUSTOMER_JWT_SECRET ||
        'http://www.bocvietnam.com ACL@2019 for customer',
      tokenExpiresHours: 720,
      jwtOptions: {
        expiresIn: '30d',
      },
    },
    internalAuth: {
      adminUser: process.env.INTERNAL_ADMIN_USER || 'admin',
      adminHashPassword:
        process.env.INTERNAL_HASH_PASSWORD ||
        'bf841dabe78bf2986a6291c02fb69123',
      hashSalt: process.env.INTERNAL_HASH_SALT || 'sHDbwWfL5gzJ5Cyk',
      jwtSecret:
        process.env.INTERNAL_JWT_SECRET ||
        'http://www.bocvietnam.com Online ACL@2019',
      tokenExpiresHours: 24,
      jwtOptions: {
        expiresIn: '24h',
      },
    },
    internalApiKey:
      process.env.INTERNAL_API_KEY || '9d59dc9bf300f17fecf22df34e6262d7',

    oapi: {
      loginUrl:
        process.env.LOGIN_URL ||
        'http://api-dev.homecredit.vn/authentication/v1/partner',
      url: process.env.OPEN_API_URL || 'http://api-dev.homecredit.vn',
      clientId: process.env.CLIENT_ID || 'X5xotBB5pvXAnnB65Vp7pOeDZtf5Xf6W',
      clientSecret:
        process.env.CLIENT_SECRECT || 'p2OSpqDKel1Tq93ljPrDaOyK9LN4oSVU',
      userName: process.env.OPEN_API_USERNAME || 'aclUser',
      password: process.env.OPEN_API_PASSWORD || 'Qwer!2345',
      authToken:
        process.env.OPEN_API_AUTH_TOKEN ||
        'T3BlbkFwaV9QYXJ0bmVyOk9wZW5BcGlfUGFydG5lcg==',
    },

    // https://developers.facebook.com/
    facebook: {
      id: process.env.FACEBOOK_APP_ID || '186244551745631',
      secret:
        process.env.FACEBOOK_APP_SECRET || 'a970ae3240ab4b9b8aae0f9f0661c6fc',
    },

    // https://cloud.google.com/console/project
    google: {
      id:
        process.env.GOOGLE_CLIENT_ID ||
        '251410730550-ahcg0ou5mgfhl8hlui1urru7jn5s12km.apps.googleusercontent.com',
      secret: process.env.GOOGLE_CLIENT_SECRET || 'Y8yR9yZAhm9jQ8FKAL8QIEcd',
    },

    // https://apps.twitter.com/
    twitter: {
      key: process.env.TWITTER_CONSUMER_KEY || 'Ie20AZvLJI2lQD5Dsgxgjauns',
      secret:
        process.env.TWITTER_CONSUMER_SECRET ||
        'KTZ6cxoKnEakQCeSpZlaUCJWGAlTEBJj0y2EMkUBujA7zWSvaQ',
    },
  },
};

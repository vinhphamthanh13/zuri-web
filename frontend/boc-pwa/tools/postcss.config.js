/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

/* eslint-disable global-require */

const pkg = require('../package.json');

module.exports = () => ({
  /**
   * ! Learn about postcss-preset-env here: https://preset-env.cssdb.org/features
   */
  plugins: [
    require('postcss-normalize'),
    require('postcss-global-import')(),
    require('postcss-import')(),
    require('postcss-preset-env')({
      stage: 0,
      browsers: pkg.browserslist,
    }),
  ],
});

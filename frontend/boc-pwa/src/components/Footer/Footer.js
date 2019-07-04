/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2018-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import PropTypes from 'prop-types';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import cx from 'classnames';
import s from './Footer.css';

class Footer extends React.PureComponent {
  static propTypes = {
    hiddenXs: PropTypes.bool,
  };
  static defaultProps = {
    hiddenXs: false,
  };

  render() {
    const rootClasses = cx(s.root, {
      [s.hiddenXs]: this.props.hiddenXs,
    });
    return (
      <div className={rootClasses}>
        <div className={s.container}>
          <span className={s.text}>© 2018 Bản quyền thuộc về http://www.bocvietnam.com</span>
        </div>
      </div>
    );
  }
}

export default withStyles(s)(Footer);

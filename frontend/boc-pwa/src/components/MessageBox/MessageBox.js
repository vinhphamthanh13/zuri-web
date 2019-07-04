import React, { PureComponent } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { string } from 'prop-types';

import s from './MessageBox.css';

class MessageBox extends PureComponent {
  static propTypes = {
    image: string,
    message: string.isRequired,
  };

  static defaultProps = {
    image: '',
  };

  render() {
    const { image, message } = this.props;

    return (
      <div className={s.notSupportMessage}>
        {image && <img src={image} className={s.notSupportImg} alt={image} />}
        <p>{message}</p>
      </div>
    );
  }
}

export default withStyles(s)(MessageBox);

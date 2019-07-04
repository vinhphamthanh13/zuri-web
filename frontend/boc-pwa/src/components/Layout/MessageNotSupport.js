import React from 'react';
import MessageBox from 'components/MessageBox';

import { DIDNT_SUPPORT } from './constant';
import sadIco from './images/sad-ico.svg';

const MessageNotSupport = () => (
  <MessageBox image={sadIco} message={DIDNT_SUPPORT} />
);

export default MessageNotSupport;

import React from 'react';
import { string } from 'prop-types';

export const HowToReg = ({ hexColor }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width="24"
    height="24"
    viewBox="0 0 24 24"
  >
    <path fillRule="evenodd" clipRule="evenodd" fill="none" d="M0 0h24v24H0z" />
    <g fillRule="evenodd" clipRule="evenodd">
      <path
        fill={hexColor}
        d="M9 17l3-2.94c-.39-.04-.68-.06-1-.06-2.67 0-8 1.34-8 4v2h9l-3-3zm2-5c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4"
      />
      <path
        fill={hexColor}
        d="M15.47 20.5L12 17l1.4-1.41 2.07 2.08 5.13-5.17 1.4 1.41z"
      />
    </g>
  </svg>
);

HowToReg.propTypes = {
  hexColor: string,
};

HowToReg.defaultProps = {
  hexColor: '#fff',
};

export const Clear = ({ hexColor }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z" />
    <path d="M0 0h24v24H0z" fill="none" />
  </svg>
);

Clear.propTypes = {
  hexColor: string,
};

Clear.defaultProps = {
  hexColor: '#fff',
};

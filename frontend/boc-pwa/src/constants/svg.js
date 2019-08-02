import React from 'react';
import { string, number } from 'prop-types';

export const HowToReg = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
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
  size: number,
};

HowToReg.defaultProps = {
  hexColor: '#fff',
  size: 24,
};

export const Clear = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z" />
    <path d="M0 0h24v24H0z" fill="none" />
  </svg>
);

Clear.propTypes = {
  hexColor: string,
  size: number,
};

Clear.defaultProps = {
  hexColor: '#fff',
  size: 24,
};

export const Reorder = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M0 0h24v24H0z" fill="none" />
    <path d="M3 15h18v-2H3v2zm0 4h18v-2H3v2zm0-8h18V9H3v2zm0-6v2h18V5H3z" />
  </svg>
);

Reorder.propTypes = {
  hexColor: string,
  size: number,
};

Reorder.defaultProps = {
  hexColor: '#fff',
  size: 24,
};

export const ShowChart = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M3.5 18.49l6-6.01 4 4L22 6.92l-1.41-1.41-7.09 7.97-4-4L2 16.99z" />
    <path fill="none" d="M0 0h24v24H0z" />
  </svg>
);

ShowChart.propTypes = {
  hexColor: string,
  size: number,
};

ShowChart.defaultProps = {
  hexColor: '#fff',
  size: 24,
};

export const FilterVintage = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M0 0h24v24H0z" fill="none" />
    <path d="M18.7 12.4c-.28-.16-.57-.29-.86-.4.29-.11.58-.24.86-.4 1.92-1.11 2.99-3.12 3-5.19-1.79-1.03-4.07-1.11-6 0-.28.16-.54.35-.78.54.05-.31.08-.63.08-.95 0-2.22-1.21-4.15-3-5.19C10.21 1.85 9 3.78 9 6c0 .32.03.64.08.95-.24-.2-.5-.39-.78-.55-1.92-1.11-4.2-1.03-6 0 0 2.07 1.07 4.08 3 5.19.28.16.57.29.86.4-.29.11-.58.24-.86.4-1.92 1.11-2.99 3.12-3 5.19 1.79 1.03 4.07 1.11 6 0 .28-.16.54-.35.78-.54-.05.32-.08.64-.08.96 0 2.22 1.21 4.15 3 5.19 1.79-1.04 3-2.97 3-5.19 0-.32-.03-.64-.08-.95.24.2.5.38.78.54 1.92 1.11 4.2 1.03 6 0-.01-2.07-1.08-4.08-3-5.19zM12 16c-2.21 0-4-1.79-4-4s1.79-4 4-4 4 1.79 4 4-1.79 4-4 4z" />
  </svg>
);

FilterVintage.propTypes = {
  hexColor: string,
  size: number,
};

FilterVintage.defaultProps = {
  hexColor: '#fff',
  size: 24,
};

export const BubbleChart = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path fill="none" d="M0 0h24v24H0z" />
    <circle cx="7.2" cy="14.4" r="3.2" />
    <circle cx="14.8" cy="18" r="2" />
    <circle cx="15.2" cy="8.8" r="4.8" />
  </svg>
);

BubbleChart.propTypes = {
  hexColor: string,
  size: number,
};

BubbleChart.defaultProps = {
  hexColor: '#fff',
  size: 24,
};

export const Announcement = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M20 2H4c-1.1 0-1.99.9-1.99 2L2 22l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm-7 9h-2V5h2v6zm0 4h-2v-2h2v2z" />
    <path d="M0 0h24v24H0z" fill="none" />
  </svg>
);

Announcement.propTypes = {
  hexColor: string,
  size: number,
};

Announcement.defaultProps = {
  hexColor: '#fff',
  size: 24,
};

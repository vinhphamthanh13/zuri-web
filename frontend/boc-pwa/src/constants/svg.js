import React from 'react';
import { string, number } from 'prop-types';

const svgProps = {
  hexColor: string,
  size: number,
};

const svgDefaultProps = {
  hexColor: '#fff',
  size: 24,
};

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
  ...svgProps,
};

HowToReg.defaultProps = {
  ...svgDefaultProps,
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
  ...svgProps,
};

Clear.defaultProps = {
  ...svgDefaultProps,
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
  ...svgProps,
};

Reorder.defaultProps = {
  ...svgDefaultProps,
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
  ...svgProps,
};

ShowChart.defaultProps = {
  ...svgDefaultProps,
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
  ...svgProps,
};

FilterVintage.defaultProps = {
  ...svgDefaultProps,
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
  ...svgProps,
};

BubbleChart.defaultProps = {
  ...svgDefaultProps,
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
  ...svgProps,
};

Announcement.defaultProps = {
  ...svgDefaultProps,
};

export const Store = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M0 0h24v24H0z" fill="none" />
    <path d="M20 4H4v2h16V4zm1 10v-2l-1-5H4l-1 5v2h1v6h10v-6h4v6h2v-6h1zm-9 4H6v-4h6v4z" />
  </svg>
);

Store.propTypes = {
  ...svgProps,
};

Store.defaultProps = {
  ...svgDefaultProps,
};

export const List = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M3 13h2v-2H3v2zm0 4h2v-2H3v2zm0-8h2V7H3v2zm4 4h14v-2H7v2zm0 4h14v-2H7v2zM7 7v2h14V7H7z" />
    <path d="M0 0h24v24H0z" fill="none" />
  </svg>
);

List.propTypes = {
  ...svgProps,
};

List.defaultProps = {
  ...svgDefaultProps,
};

export const ViewStream = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M4 18h17v-6H4v6zM4 5v6h17V5H4z" />
    <path d="M0 0h24v24H0z" fill="none" />
  </svg>
);

ViewStream.propTypes = {
  ...svgProps,
};

ViewStream.defaultProps = {
  ...svgDefaultProps,
};

export const SupervisedUser = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M11.99 2c-5.52 0-10 4.48-10 10s4.48 10 10 10 10-4.48 10-10-4.48-10-10-10zm3.61 6.34c1.07 0 1.93.86 1.93 1.93 0 1.07-.86 1.93-1.93 1.93-1.07 0-1.93-.86-1.93-1.93-.01-1.07.86-1.93 1.93-1.93zm-6-1.58c1.3 0 2.36 1.06 2.36 2.36 0 1.3-1.06 2.36-2.36 2.36s-2.36-1.06-2.36-2.36c0-1.31 1.05-2.36 2.36-2.36zm0 9.13v3.75c-2.4-.75-4.3-2.6-5.14-4.96 1.05-1.12 3.67-1.69 5.14-1.69.53 0 1.2.08 1.9.22-1.64.87-1.9 2.02-1.9 2.68zM11.99 20c-.27 0-.53-.01-.79-.04v-4.07c0-1.42 2.94-2.13 4.4-2.13 1.07 0 2.92.39 3.84 1.15-1.17 2.97-4.06 5.09-7.45 5.09z" />
    <path fill="none" d="M0 0h24v24H0z" />
  </svg>
);

SupervisedUser.propTypes = {
  ...svgProps,
};

SupervisedUser.defaultProps = {
  ...svgDefaultProps,
};

export const MonetizationOn = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1.41 16.09V20h-2.67v-1.93c-1.71-.36-3.16-1.46-3.27-3.4h1.96c.1 1.05.82 1.87 2.65 1.87 1.96 0 2.4-.98 2.4-1.59 0-.83-.44-1.61-2.67-2.14-2.48-.6-4.18-1.62-4.18-3.67 0-1.72 1.39-2.84 3.11-3.21V4h2.67v1.95c1.86.45 2.79 1.86 2.85 3.39H14.3c-.05-1.11-.64-1.87-2.22-1.87-1.5 0-2.4.68-2.4 1.64 0 .84.65 1.39 2.67 1.91s4.18 1.39 4.18 3.91c-.01 1.83-1.38 2.83-3.12 3.16z" />
    <path fill="none" d="M0 0h24v24H0z" />
  </svg>
);

MonetizationOn.propTypes = {
  ...svgProps,
};

MonetizationOn.defaultProps = {
  ...svgDefaultProps,
};

export const Print = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M19 8H5c-1.66 0-3 1.34-3 3v6h4v4h12v-4h4v-6c0-1.66-1.34-3-3-3zm-3 11H8v-5h8v5zm3-7c-.55 0-1-.45-1-1s.45-1 1-1 1 .45 1 1-.45 1-1 1zm-1-9H6v4h12V3z" />
    <path d="M0 0h24v24H0z" fill="none" />
  </svg>
);

Print.propTypes = {
  ...svgProps,
};

Print.defaultProps = {
  ...svgDefaultProps,
};

export const ArrowBack = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M11.67 3.87L9.9 2.1 0 12l9.9 9.9 1.77-1.77L3.54 12z" />
    <path fill="none" d="M0 0h24v24H0z" />
  </svg>
);

ArrowBack.propTypes = {
  ...svgProps,
};

ArrowBack.defaultProps = {
  ...svgDefaultProps,
};

export const ArrowForward = ({ hexColor, size }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill={hexColor}
  >
    <path d="M5.88 4.12L13.76 12l-7.88 7.88L8 22l10-10L8 2z" />
    <path fill="none" d="M0 0h24v24H0z" />
  </svg>
);

ArrowForward.propTypes = {
  ...svgProps,
};

ArrowForward.defaultProps = {
  ...svgDefaultProps,
};

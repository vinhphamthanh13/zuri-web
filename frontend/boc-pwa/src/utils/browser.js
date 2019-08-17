import { isMobile } from 'react-device-detect';
import history from '../history';

export const hasGetUserMedia = () =>
  !!(
    (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) ||
    navigator.webkitGetUserMedia ||
    navigator.mozGetUserMedia ||
    navigator.msGetUserMedia
  );

export const goBack = () => {
  history.goBack();
};

export const goForward = () => {
  history.goForward();
};

export const resolveDimension = (width, height) => {
  if (width === 320 || height === 320) return null;
  return {
    width: `${width}px`,
    height: width > height && isMobile ? `${width}px` : `${height}px`,
  };
};

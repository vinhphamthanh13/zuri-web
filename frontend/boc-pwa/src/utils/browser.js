import { isMobile } from 'react-device-detect';
import { GOOGLE_CAPTCHA_SCRIPT } from 'constants/common';
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
export const navigateTo = (url, state) => {
  if (typeof state === 'object') return history.push(url, { ...state });
  return history.push(url);
};
export const blockNavigation = message => history.block(message);
export const goForward = () => {
  history.goForward();
};

export const resolveDimension = (width, height) => {
  if (width < 321 || height < 321) return null;
  return {
    width: `${width}px`,
    height: width > height && isMobile ? `${width}px` : `${height}px`,
  };
};

export const injectGoogleCaptchaScript = (d, scr, id) => {
  if (d.getElementById(id)) return;
  const gCaptcha = d.getElementsByTagName(scr)[0];
  const script = d.createElement(scr);
  script.id = id;
  script.src = GOOGLE_CAPTCHA_SCRIPT;
  gCaptcha.parentNode.insertBefore(script, gCaptcha);
};

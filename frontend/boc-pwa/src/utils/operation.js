export const getOperatingSystem = () => {
  let OSName = 'Unknown OS';
  if (navigator.appVersion.indexOf('Win') !== -1) OSName = 'Windows';
  if (navigator.appVersion.indexOf('Mac') !== -1) OSName = 'MacOS';
  if (navigator.appVersion.indexOf('X11') !== -1) OSName = 'UNIX';
  if (navigator.appVersion.indexOf('Linux') !== -1) OSName = 'Linux';

  return OSName;
};

export const getBrowserName = () => {
  let name = 'Unknown';
  if (navigator.userAgent.indexOf('MSIE') !== -1) {
    name = 'MSIE';
  } else if (navigator.userAgent.indexOf('Firefox') !== -1) {
    name = 'Firefox';
  } else if (navigator.userAgent.indexOf('Opera') !== -1) {
    name = 'Opera';
  } else if (
    navigator.userAgent.indexOf('Chrome') !== -1 ||
    navigator.userAgent.match('CriOS')
  ) {
    name = 'Chrome';
  } else if (navigator.userAgent.indexOf('Safari') !== -1) {
    name = 'Safari';
  }
  return name;
};

export const isMacOS = () => getOperatingSystem() === 'MacOS';

export const isSafari = () => getBrowserName() === 'Safari';

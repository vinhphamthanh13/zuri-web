/**
 * Send event to Google Analystic
 *
 * @param  {String} action Action type
 * @param  {String} label  Event label
 * @return {void}
 */
export const sendGAEvent = (action, label, category = 'ACL O2O') => {
  window.ga('send', 'event', {
    eventCategory: category,
    eventAction: action,
    eventLabel: label,
  });
};

/**
 * Send timming to Google Analystic
 *
 * @param  {String} timmingVar   Timming var
 * @param  {Number} time         Time
 * @return {void}
 */
export const sendGATimming = (timmingVar, time, category = 'ACL O2O') => {
  window.ga('send', 'timing', {
    timingCategory: category,
    timingVar: timmingVar,
    timingValue: time,
  });
};

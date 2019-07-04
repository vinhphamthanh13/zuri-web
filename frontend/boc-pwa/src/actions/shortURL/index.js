/**
 * Action generate short URL
 *
 * @param {string} saCode
 *
 * @return {Object} shortId generated from saCode
 */
export const generateShortURL = saCode =>
  fetch(`/api/generateShortURL/${saCode}`, {
    method: 'GET',
  })
    .then(body => body.json())
    .catch(error => console.error('Error:', error));

/**
 * Action verify short Id to get sa code back
 *
 * @param {string} shortId
 *
 * @return {Object} shortId generated from saCode
 */
export const verifyShortId = shortId =>
  fetch(`/api/verifyShortId/${shortId}`, {
    method: 'GET',
  })
    .then(body => body.json())
    .catch(error => console.error('Error:', error));

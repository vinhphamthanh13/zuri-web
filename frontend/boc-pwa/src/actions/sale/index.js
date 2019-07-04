/**
 * Action verify SA code is valid or not
 *
 * @param {string} saCode
 *
 * @return {Object} { isValid: true/false } SA Code is valid or not
 */
export async function verifySACode(saCode) {
  return fetch(`/api/sale/verify/${saCode}`, {
    method: 'GET',
  })
    .then(body => body.json())
    .catch(error => console.error('Error:', error));
}

/**
 * Async action for fetching near shop
 * @param {Number} lat
 * @param {number} long
 */
export async function sendEmail(contractId, appointmentInfo) {
  return fetch('/api/sendEmail', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      contractId,
      appointmentInfo,
    }),
    credentials: 'include',
  })
    .then(body => body.json())
    .catch(error => console.error('Error:', error));
}

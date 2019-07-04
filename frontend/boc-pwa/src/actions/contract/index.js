/**
 * Async action for fetching near shop
 * @param {Number} contractId
 */
export default async function getContractDate(contractId) {
  return fetch(`/api/contract/${contractId}`, {
    method: 'GET',
  })
    .then(body => body.json())
    .catch(error => console.error('Error:', error));
}

import { UPDATE_LIST_REGION, UPDATE_LIST_DISTRICT } from 'constants/clz';

/**
 * Action creator for update Regions
 * @param {Array} regions
 */
export function updateListRegion(regions) {
  return {
    type: UPDATE_LIST_REGION,
    regions,
  };
}

/**
 * Action creator for districts
 * @param {Array} districts
 */
export function updateListDistrict(districts) {
  return {
    type: UPDATE_LIST_DISTRICT,
    districts,
  };
}

/**
 * Async action for fetching regions
 */
export function getRegions() {
  return async dispatch => {
    try {
      const response = await fetch('/api/getRegions', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
      });
      const data = await response.json();
      dispatch(updateListRegion(data));
    } catch (error) {
      console.error('Error:', error);
    }
  };
}

/**
 * Async Action for fetching districts
 * @param {String} regionCode
 */
export function getDistricts(regionCode) {
  return async dispatch => {
    try {
      const response = await fetch('/api/getDistricts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          regionCode,
        }),
        credentials: 'include',
      });

      const data = await response.json();
      dispatch({
        type: UPDATE_LIST_DISTRICT,
        districts: data,
      });
    } catch (error) {
      console.error('Error:', error);
    }
  };
}

/**
 * Async action for fetching near shop
 * @param {Number} lat
 * @param {number} long
 */
export async function searchPOS(lat, long) {
  return fetch('/api/searchPOS', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ lat, long }),
    credentials: 'include',
  })
    .then(body => body.json())
    .catch(error => console.error('Error:', error));
}

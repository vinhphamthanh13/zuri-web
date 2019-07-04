import { UPDATE_LIST_REGION, UPDATE_LIST_DISTRICT } from 'constants/clz';
import { chain, cloneDeep } from 'lodash';

const defaultState = {
  regions: [],
  districts: [],
};

/**
 *
 * @param {Object} location
 * This function to serialize object
 */
const formatLocation = ({ name, code }) => ({
  label: name,
  value: code,
});

const excludeListRegions = ['QD', 'CS'];

const compareLocationLabel = (pre, curr) => pre.label.localeCompare(curr.label);

/**
 *
 * @param {Object} regions {name, code}
 */
const filterRegions = ({ code }) => !excludeListRegions.includes(code);

export default (state, payload) => {
  const newState = state || defaultState;

  switch (payload.type) {
    case UPDATE_LIST_REGION: {
      const regions = cloneDeep(payload.regions) || [];
      return {
        ...newState,
        regions: chain(regions)
          .filter(filterRegions)
          .map(formatLocation)
          .value()
          .sort(compareLocationLabel),
      };
    }
    case UPDATE_LIST_DISTRICT: {
      const districts = cloneDeep(payload.districts) || [];
      return {
        ...newState,
        districts: chain(districts)
          .map(formatLocation)
          .value()
          .sort(compareLocationLabel),
      };
    }
    default:
      return newState;
  }
};

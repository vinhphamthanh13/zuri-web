import React, { PureComponent } from 'react';

import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { Select, Option } from 'components/common/Select';
import findIndex from 'lodash/findIndex';

import { compose } from 'redux';
import { getRegions, getDistricts, updateListDistrict } from 'actions/map';
import { connect } from 'react-redux';
import { func, arrayOf, object, string } from 'prop-types';
import noop from 'lodash/noop';

import { PROVINCE_PLACEHOLDER, CITY_PLACEHOLDER } from './constant';
import s from './LocationFilter.css';

class LocationFilter extends PureComponent {
  static propTypes = {
    regions: arrayOf(object).isRequired,
    districts: arrayOf(object).isRequired,
    getRegionsAction: func,
    getDistrictsAction: func,
    clearDistrictsAction: func,
    contractId: string,
  };

  static defaultProps = {
    getRegionsAction: noop,
    getDistrictsAction: noop,
    clearDistrictsAction: noop,
    contractId: '',
  };

  constructor(props) {
    super(props);

    this.state = {
      regionsValue: '',
      districtsValue: '',
    };
  }

  componentDidMount() {
    const { getRegionsAction, regions, clearDistrictsAction } = this.props;

    if (!regions.length) {
      getRegionsAction();
    }
    clearDistrictsAction();
  }

  /**
   * Handle on Region change
   */
  onRegionsChange = async (name, value) => {
    const { getDistrictsAction, contractId } = this.props;
    const address = value.label;

    this.handleOnAddressChange(address);
    getDistrictsAction(value.value);

    this.setState({
      regionsValue: value.value,
      districtsValue: '',
    });

    if (contractId) {
      // ga
      window.ga('send', 'event', {
        eventCategory: 'POS Map',
        eventAction: 'Filter_province',
        eventLabel: 'Province',
      });
    }
  };

  /**
   * Handle on district change
   */
  onDistrictChange = async (name, value) => {
    this.setState({
      districtsValue: value.value,
    });

    const { regions, contractId } = this.props;
    const { regionsValue } = this.state;
    const regionValueIndex = findIndex(
      regions,
      region => region.value === regionsValue,
    );
    const regionLabel = regions[regionValueIndex].label;
    const fullAdress = `${regionLabel} ${value.label}`;

    this.handleOnAddressChange(fullAdress);

    if (contractId) {
      // ga
      window.ga('send', 'event', {
        eventCategory: 'POS Map',
        eventAction: 'Filter_District',
        eventLabel: 'District',
      });
    }
  };

  /**
   * Handle on address change
   */
  handleOnAddressChange = async address => {
    const { setMapCenter } = this.props;
    const response = await fetch(
      `https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyB6roPl8-APM6Y8Bh_U6D-RSSiWhk5hXDA&address=${address}`,
    );
    const { results } = await response.json();
    const { lat, lng } = results[0].geometry.location;

    setMapCenter(lat, lng);
  };

  render() {
    const { regionsValue, districtsValue } = this.state;
    const { regions, districts } = this.props;

    return (
      <div className={s.filterBox}>
        <Select
          placeholder={PROVINCE_PLACEHOLDER}
          onChange={this.onRegionsChange}
          name="region"
          value={regionsValue}
          filterable
          className={s.locationSelect}
        >
          {regions.length &&
            regions.map(region => (
              <Option
                key={region.value}
                value={region.value}
                label={region.label}
              />
            ))}
        </Select>
        <Select
          placeholder={CITY_PLACEHOLDER}
          onChange={this.onDistrictChange}
          name="districts"
          value={districtsValue}
          filterable
          className={s.locationSelect}
        >
          {districts.length &&
            districts.map(district => (
              <Option
                key={district.value}
                value={district.value}
                label={district.label}
              />
            ))}
        </Select>
      </div>
    );
  }
}

const mapStateToProps = ({ clz }) => clz.map;

const mapDispatchToProps = dispatch => ({
  getRegionsAction: () => {
    dispatch(getRegions());
  },
  getDistrictsAction: regionCode => {
    dispatch(getDistricts(regionCode));
  },
  clearDistrictsAction: () => {
    dispatch(updateListDistrict([]));
  },
});

const enhance = compose(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  ),
  withStyles(s),
);

export default enhance(LocationFilter);

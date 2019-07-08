/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
import React from 'react';
import { compose } from 'redux';
import { connect } from 'react-redux';
import { func, number, shape, array, element, oneOfType } from 'prop-types';
import { noop } from 'lodash';
import { withScriptjs, withGoogleMap, GoogleMap } from 'react-google-maps';

import { MAP_URL, DEFAULT_LOCATION } from './constant';

class Map extends React.Component {
  static propTypes = {
    defaultCenter: shape({
      lat: number,
      lng: number,
    }),
    mapCenter: shape({
      lat: number,
      lng: number,
    }),
    onMapMounted: func,
    onMapIdle: func,
    onClick: func,
    defaultZoom: number,
    children: oneOfType([element, array]),
  };

  static defaultProps = {
    defaultCenter: DEFAULT_LOCATION,
    mapCenter: DEFAULT_LOCATION,
    defaultZoom: 13,
    onMapMounted: noop,
    onMapIdle: noop,
    onClick: noop,
    children: [],
  };

  render() {
    const {
      onMapMounted,
      onMapIdle,
      onClick,
      defaultZoom,
      mapCenter,
      defaultCenter,
    } = this.props;
    return (
      <GoogleMap
        defaultZoom={defaultZoom}
        defaultCenter={defaultCenter}
        center={mapCenter}
        ref={onMapMounted}
        onIdle={onMapIdle}
        onClick={onClick}
        defaultOptions={{ mapTypeControl: false, streetViewControl: false }}
      >
        {this.props.children}
      </GoogleMap>
    );
  }
}

const createContainerElemnt = () => {
  const style = {
    height: `calc(${window.innerHeight}px - 50px)`,
  };
  return <div style={style} />;
};

const mapStateToProps = () => ({
  googleMapURL: MAP_URL,
  loadingElement: <div style={{ height: `100%` }}>Loading Map...</div>,
  containerElement: createContainerElemnt(),
  mapElement: <div style={{ height: `100%` }} />,
});

const enhance = compose(
  connect(mapStateToProps),
  withScriptjs,
  withGoogleMap,
);

export default enhance(Map);

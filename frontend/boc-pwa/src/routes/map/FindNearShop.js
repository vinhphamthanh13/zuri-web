import React from 'react';
import { Marker } from 'react-google-maps';
import { searchPOS } from 'actions/map';
import getContractDate from 'actions/contract';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { isMacOS } from 'utils/operation';
import concat from 'lodash/concat';
import differenceBy from 'lodash/differenceBy';
import { string } from 'prop-types';
import find from 'lodash/find';
import { FullLoading, Cycle } from 'homecredit-ui';
import InvaidlUrlMessage from './InvaidlUrlMessage';
import ContractExpire from './ContractExpire';

import ModalStep from './ModalStep';
import CustMapControl from './CustMapControl';
import PosShopMarker from './PosShopMarker';
import Map from './Map';
import LocationFilter from './LocationFilter';
import {
  TOP_LEFT,
  RIGHT_BOTTOM,
  DEFAULT_LOCATION,
  LATEST_HOUR,
} from './constant';
import markerCurrent from './images/markerCurrent.png';
import s from './FindNearShop.css';
import { milisecondOfDay, getDateFromString } from '../../utils/time';

const FullLoadingCycle = FullLoading(Cycle);

class FindNearShop extends React.Component {
  static propTypes = {
    contractId: string,
  };

  static defaultProps = {
    contractId: '',
  };

  state = {
    mapCenter: DEFAULT_LOCATION,
    mapRef: null,
    shops: [],
    showInfoIndex: null,
    userLocation: null,
    isOpenModal: false,
    contractCreateAt: '',
    isInvalidContract: false,
  };

  componentDidMount = async () => {
    this.loadContractInfo();
  };

  /**
   * Make sure if map/:id -> map
   * Fetch contract again
   * @return {void} {description}
   */
  componentDidUpdate(prevProps) {
    const { contractId } = this.props;
    if (prevProps.contractId !== contractId) {
      // reset state
      this.resetContractState();
      this.loadContractInfo();
    }
  }

  /**
   * Get ref to map
   */
  onMapMounted = ref => {
    this.setState(() => ({
      mapRef: ref,
    }));

    this.requestForUserLocation().then(({ coords }) => {
      const lat = coords.latitude;
      const lng = coords.longitude;

      this.setState({
        mapCenter: { lat, lng },
        userLocation: { lat, lng },
      });
    });
  };

  /**
   * Handle for map idle
   * First time run at init (Request shop around)
   * And when user drog happen (Request near shop again)
   */
  onMapIdle = async () => {
    const { mapRef } = this.state;

    if (mapRef) {
      const { lat, lng } = this.mapCenter;
      try {
        const newShops = await searchPOS(lat, lng);
        const mergedShops = differenceBy(
          newShops,
          this.state.shops,
          'ShopCode',
        );

        this.setState(({ shops }) => ({
          shops: concat(shops, mergedShops),
        }));
      } catch (e) {
        console.error(e);
      }
    }
  };

  /**
   * We have this event handler
   * becasue it block click event to document
   * and Select detect click outside base on document click
   */
  onMapClick = () => {
    const { activeElement } = document;

    document.dispatchEvent(new Event('mousedown'));
    if (activeElement && activeElement.blur) {
      activeElement.blur();
    }
  };

  /**
   * Detect if contract is expired
   * @return {void}
   */
  get isExpired() {
    const { contractCreateAt } = this.state;

    if (!contractCreateAt) return false;

    const today = new Date();
    const appointmentDate = new Date(
      getDateFromString(contractCreateAt).getTime() + milisecondOfDay * 6,
    );
    appointmentDate.setHours(LATEST_HOUR);

    return appointmentDate.getTime() < today.getTime();
  }

  /**
   * Return map center location
   * @return {Object} {Return location object}
   */
  get mapCenter() {
    const { mapRef } = this.state;
    const centerLocation = mapRef.getCenter();
    const lat = centerLocation.lat();
    const lng = centerLocation.lng();

    return { lat, lng };
  }

  /**
   * Set map center
   * @param {Number} lat
   * @param {Number} lng
   */
  setMapCenter = (lat, lng) => {
    this.setState({
      mapCenter: { lat, lng },
    });
  };

  /**
   * Reset state of contract
   * @return {void}
   */
  resetContractState = () => {
    this.setState(() => ({
      contractCreateAt: '',
      isInvalidContract: false,
    }));
  };

  /**
   * Load contract info base in contractId
   * @return {void}
   */
  loadContractInfo = async () => {
    const { contractId } = this.props;

    if (contractId) {
      window.ga('send', 'event', {
        eventCategory: 'POS Map',
        eventAction: 'Visit_POS_map',
        eventLabel: 'POS_map',
      });
      const contract = await getContractDate(contractId);

      if (!contract || contract.errors) {
        this.setState(() => ({
          isInvalidContract: true,
        }));
      } else {
        this.setState(() => ({
          contractCreateAt: contract.createAt,
        }));
      }
    }
  };

  /**
   * Request user location
   * @return {Promise} resolve
   */
  requestForUserLocation = () => {
    const { geolocation } = navigator;

    if (geolocation) {
      return new Promise((res, rej) => {
        geolocation.getCurrentPosition(res, rej);
      });
    }
    return false;
  };

  /**
   * @function Handle marker on click
   * @param  {type} handleMarkerClick {description}
   * @return {Function} {description}
   */
  handleMarkerClick = (e, shop) => {
    const { mapRef } = this.state;
    // override panto when click
    const lat = e.latLng.lat();
    const lng = e.latLng.lng();

    mapRef.panTo({ lat, lng });
    mapRef.panBy(0, -150);

    // set shopcode to show infowindow
    this.setState({
      showInfoIndex: shop.ShopCode,
    });
  };

  /**
   * Handle when Infowindow close
   * Set showInfoIndex is empty
   * Aviod user click back to marker did not show again
   */
  handleInfoWindowClose = () => {
    this.setState({
      showInfoIndex: '',
    });
  };

  /**
   * Focus to user location
   */
  focusUserLocation = async () => {
    try {
      const { coords } = await this.requestForUserLocation();
      const { latitude: lat, longitude: lng } = coords;

      this.setState({
        mapCenter: { lat, lng },
        userLocation: { lat, lng },
      });
    } catch (errors) {
      console.error(errors);
    }
  };

  openModal = () => {
    this.setState({
      isOpenModal: true,
    });
  };

  closeModal = () => {
    this.setState({
      isOpenModal: false,
    });
  };

  render() {
    const {
      shops,
      mapCenter,
      showInfoIndex,
      userLocation,
      isOpenModal,
      contractCreateAt,
      isInvalidContract,
    } = this.state;
    const filterLocationCss = isMacOS() ? s.filterWrapperFull : s.filterWrapper;
    const { contractId } = this.props;
    const isShowLoading = contractId && !contractCreateAt && !isInvalidContract;

    return (
      <div className={s.wrapperFindNear}>
        {!isInvalidContract &&
          !this.isExpired && (
            <Map
              onMapMounted={this.onMapMounted}
              onMapIdle={this.onMapIdle}
              onClick={this.onMapClick}
              mapCenter={mapCenter}
            >
              {shops.map(shop => (
                <PosShopMarker
                  key={shop.ShopCode}
                  shop={shop}
                  onClick={this.handleMarkerClick}
                  onInfoWindowClose={this.handleInfoWindowClose}
                  showInfoIndex={showInfoIndex === shop.ShopCode}
                  contractId={contractId}
                  makeAppointment={this.openModal}
                />
              ))}

              {userLocation && (
                <Marker
                  position={{ lat: userLocation.lat, lng: userLocation.lng }}
                  icon={markerCurrent}
                  animation={2}
                />
              )}

              <CustMapControl position={TOP_LEFT} className={filterLocationCss}>
                <LocationFilter
                  setMapCenter={this.setMapCenter}
                  contractId={contractId}
                />
              </CustMapControl>
              <CustMapControl
                position={RIGHT_BOTTOM}
                className={s.currentLocation}
              >
                <button
                  className={s.btnCurrentLocation}
                  onClick={this.focusUserLocation}
                >
                  <span className={s.btnCurrentLocationIcon} />
                </button>
              </CustMapControl>
            </Map>
          )}

        {isShowLoading && <FullLoadingCycle fullPage />}

        {isInvalidContract && (
          <div className={s.messageWrapper}>
            <InvaidlUrlMessage />
          </div>
        )}

        {this.isExpired && (
          <div className={s.messageWrapper}>
            <ContractExpire />
          </div>
        )}

        <ModalStep
          isOpen={isOpenModal}
          onClose={this.closeModal}
          shop={find(shops, shop => shop.ShopCode === showInfoIndex)}
          contractId={contractId}
          contractCreateAt={contractCreateAt}
        />
      </div>
    );
  }
}

export default withStyles(s)(FindNearShop);

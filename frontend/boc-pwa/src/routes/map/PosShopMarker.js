import React, { PureComponent, Fragment } from 'react';
import { Marker, InfoWindow } from 'react-google-maps';
import { func, shape, bool, string, number } from 'prop-types';
import noop from 'lodash/noop';
import { Button } from 'homecredit-ui';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './PosShopMarker.css';

class PosShopMarker extends PureComponent {
  static propTypes = {
    shop: shape({
      Address: string,
      Identity: string,
      Latitude: number,
      Longitude: number,
      ShopCode: string,
      Title: string,
    }).isRequired,
    onClick: func,
    showInfoIndex: bool,
    onInfoWindowClose: func,
    contractId: string,
    makeAppointment: func,
  };

  static defaultProps = {
    onClick: noop,
    showInfoIndex: false,
    onInfoWindowClose: noop,
    contractId: '',
    makeAppointment: noop,
  };

  /**
   * Handle click marker
   */
  handleMarkerClick = e => {
    const { shop, onClick, contractId } = this.props;

    onClick(e, shop);

    // ga
    if (contractId) {
      window.ga('send', 'event', {
        eventCategory: 'POS Map',
        eventAction: 'Select_POS',
        eventLabel: 'POS',
      });
    }
  };

  /**
   * Handle on user click pointment
   * @return {type} {description}
   */
  handleMakePointment = () => {
    this.props.makeAppointment();

    // ga
    window.ga('send', 'event', {
      eventCategory: 'POS Map',
      eventAction: 'Select_time_appointment',
      eventLabel: 'Time_appointment',
    });
  };

  render() {
    const { shop, showInfoIndex, onInfoWindowClose, contractId } = this.props;
    const { ShopCode, Latitude, Longitude, Title, Address } = shop;

    return (
      <Fragment>
        <Marker
          key={ShopCode}
          position={{ lat: Latitude, lng: Longitude }}
          title={Title}
          onClick={this.handleMarkerClick}
        >
          {showInfoIndex && (
            <InfoWindow onCloseClick={onInfoWindowClose}>
              <div>
                <strong>{Title}</strong>
                <p>{Address}</p>
                {contractId && (
                  <Button
                    className={s.btnAppointment}
                    onClick={this.handleMakePointment}
                  >
                    Đặt lịch hẹn
                  </Button>
                )}
              </div>
            </InfoWindow>
          )}
        </Marker>
      </Fragment>
    );
  }
}

export default withStyles(s)(PosShopMarker);

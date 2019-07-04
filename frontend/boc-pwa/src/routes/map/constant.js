import { chain } from 'lodash';

export const MAP_URL =
  'https://maps.googleapis.com/maps/api/js?key=AIzaSyB6roPl8-APM6Y8Bh_U6D-RSSiWhk5hXDA';
export const PROVINCE_PLACEHOLDER = 'Chọn tỉnh/ thành phố';
export const CITY_PLACEHOLDER = 'Chọn quận/ huyện';

// google maps postion
// use this instead of google.map
// because sometime google.map notworking
export const BOTTOM = 11;
export const BOTTOM_CENTER = 11;
export const BOTTOM_LEFT = 10;
export const BOTTOM_RIGHT = 12;
export const CENTER = 13;
export const LEFT = 5;
export const LEFT_BOTTOM = 6;
export const LEFT_CENTER = 4;
export const LEFT_TOP = 5;
export const RIGHT = 7;
export const RIGHT_BOTTOM = 9;
export const RIGHT_CENTER = 8;
export const RIGHT_TOP = 7;
export const TOP = 2;
export const TOP_CENTER = 2;
export const TOP_LEFT = 1;
export const TOP_RIGHT = 3;

export const DEFAULT_LOCATION = { lat: 10.778995, lng: 106.6940313 };

// Modal Appointment
export const HOURS_APPOINTMENT = [
  {
    start: '8:00',
    end: '10:00',
  },
  {
    start: '10:00',
    end: '12:00',
  },
  {
    start: '12:00',
    end: '14:00',
  },
  {
    start: '14:00',
    end: '16:00',
  },
  {
    start: '16:00',
    end: '18:00',
  },
  {
    start: '18:00',
    end: '20:00',
  },
];

export const MODAL_CONTENT = [
  {
    FOOTER_TITLE: 'Đặt lịch hẹn',
  },
  {
    FOOTER_TITLE: 'Xác nhận',
  },
];

export const CANCEL_BTN = 'Hủy';

export const MAKE_APPOINTMENT = 'Đặt lịch hẹn';

export const CONGRATULATION = 'Chúc mừng Quý Khách!';

export const STEP_ORDER_HOUR = 1;
export const STEP_SUMMARY = 2;
export const ERROR = 'error';

export const ORDER_DAY_PLACEHOLDER = 'Ngày đặt lịch';

const getLatestHourAppointment = () => {
  const latest = chain(HOURS_APPOINTMENT)
    .last()
    .get('end')
    .split(':')
    .head()
    .value();

  return latest;
};

export const LATEST_HOUR = getLatestHourAppointment();
export const LIST_STORE = 'Danh sách cửa hàng';

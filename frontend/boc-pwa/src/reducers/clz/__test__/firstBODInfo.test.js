/* eslint-env jest */
/* eslint-disable padded-blocks, no-unused-expressions */

import {
  CLZ_UPDATE_1BOD_INFO,
  DEFAULT_CREDIT_AMOUNT,
  DEFAULT_TENOR,
} from 'constants/clz';

import firstBODInfo from '../firstBODInfo';

describe('firstBODInfo reducer', () => {
  const initial1BOD = {
    phoneNumber: '',
    acceptedTerm: false,
    otp: '',
    creditAmount: DEFAULT_CREDIT_AMOUNT,
    tenor: DEFAULT_TENOR,
    firstName: '',
    lastName: '',
    middleName: '',
    dayOfBirth: '',
    gender: 'MALE',
    idCard: '',
    userImage: undefined,
    monthlyIncome: '',
    monthlyPayment: 0,
    identificationType: '',
    drivingLicence: '',
    familyBook: '',
    verificationID: null,
    offerList: [],
    bestOffer: {},
    retailAgent: '',
    sa: null,
    partnerCode: null,
    tipperCode: null,
  };

  it('should return the initial state', () => {
    expect(firstBODInfo(undefined, {})).toEqual(initial1BOD);
  });

  it('should handle CLZ_UPDATE_1BOD_INFO to update phone number', () => {
    const expectedAction = {
      label: 'phoneNumber',
      value: '09090809',
    };

    expect(
      firstBODInfo(undefined, {
        type: CLZ_UPDATE_1BOD_INFO,
        payload: expectedAction,
      }),
    ).toEqual({ ...initial1BOD, [expectedAction.label]: expectedAction.value });
  });
});

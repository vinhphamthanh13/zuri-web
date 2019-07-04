import fetchMock from 'fetch-mock';
import expect from 'expect';

import { CLZ_UPDATE_1BOD_INFO, CLZ_UPDATE_FORM_STATUS } from 'constants/clz';
import { FORM_STATUS } from 'constants/common';
import { URL } from 'constants/api';

import { set1BODInfo, setFormStatus, generateOTP } from 'actions/clz';

/* eslint-env jest */
/* eslint-disable padded-blocks, no-unused-expressions */

describe('Client zone actions', () => {
  it('should create an action to set 1BOD information', () => {
    const value = 'Name';
    const label = 'firstName';
    const expectedAction = {
      type: CLZ_UPDATE_1BOD_INFO,
      payload: { value, label },
    };

    expect(set1BODInfo({ value, label })).toEqual(expectedAction);
  });

  it('should create an action to set form status', () => {
    const value = FORM_STATUS.NEED_VALIDATE;
    const expectedAction = {
      type: CLZ_UPDATE_FORM_STATUS,
      payload: value,
    };

    expect(setFormStatus(value)).toEqual(expectedAction);
  });
});

describe('Async Action', () => {
  afterEach(() => {
    fetchMock.restore();
  });

  it('can POST generate OTP successfully', () => {
    const expectedData = { verficationId: 'xhf3jk' };

    fetchMock.post(URL.GENERATE_OTP, expectedData);

    generateOTP({ phoneNumber: '0909888222' }).then(data => {
      expect(data).toEqual(expectedData);
    });
  });
});

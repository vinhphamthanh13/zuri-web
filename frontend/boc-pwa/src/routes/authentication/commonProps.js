import {
  setUsers,
  setShopPhoneAction,
  setVerifiedCode,
} from 'actions/authenticationActions';

export const activationProps = {
  mapStateToProps: ({ authentication }) => ({
    phoneNumber: authentication.phoneNumber,
    getVerifiedCodeStatus: authentication.getVerifiedCodeStatus,
  }),
  mapDispatchToProps: dispatch => ({
    fetchUsers: () => dispatch(setUsers()),
    setPhoneNumber: number => dispatch(setShopPhoneAction(number)),
    getVerifiedCode: (countryCode, phoneNumber) =>
      dispatch(setVerifiedCode(countryCode, phoneNumber)),
  }),
};

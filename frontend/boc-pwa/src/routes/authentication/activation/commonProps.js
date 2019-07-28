import { setPhoneNumberAction, loginPhoneAction } from 'actions/authentication';

export const mapDispatchToProps = dispatch => ({
  setPhoneNumber(number) {
    dispatch(setPhoneNumberAction(number));
  },
  loginPhone(number) {
    dispatch(loginPhoneAction(number));
  },
});

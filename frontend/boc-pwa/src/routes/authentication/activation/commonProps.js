import { setPhoneNumberAction } from 'actions/authentication';

export const mapDispatchToProps = dispatch => ({
  setPhoneNumber(number) {
    dispatch(setPhoneNumberAction(number));
  },
});

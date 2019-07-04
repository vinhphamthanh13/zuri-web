import {
  set1BODInfo,
  setFormStatus,
  generateOTPSuccess,
  updateLoading,
  updateStep,
  storeTrackingToken,
} from 'actions/clz';

export const mapStateToProps = ({ clz }) => ({
  firstBODInfo: clz.firstBODInfo,
  formStatus: clz.formStatus,
  step: clz.step,
  trackingToken: clz.trackingToken,
});

export const mapDispatchToProps = dispatch => ({
  onChange1BODInfo(label, value) {
    dispatch(set1BODInfo({ label, value }));
  },
  updateFormStatus(value) {
    dispatch(setFormStatus(value));
  },
  getOTPSuccess(response) {
    dispatch(generateOTPSuccess(response));
  },
  updateLoadingStatus(status) {
    dispatch(updateLoading(status));
  },
  updateStep(step) {
    dispatch(updateStep(step));
  },
  getTrackingToken(token) {
    dispatch(storeTrackingToken(token));
  },
});

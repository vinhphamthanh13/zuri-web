import { nodeGettingStoreApi } from 'actions/shopsActions';

export const shopsProps = {
  mapStateToProps: ({ shops, authentication: { accessToken } }) => ({
    ...shops,
    accessToken,
  }),
  mapDispatchToProps: dispatch => ({
    dispatchGettingStoreInfo: (id, token) =>
      dispatch(nodeGettingStoreApi(id, token)),
  }),
};

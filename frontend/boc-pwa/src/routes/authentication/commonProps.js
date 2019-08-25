import { setUsers } from 'actions/authenticationActions';

export const activationProps = {
  mapDispatchTopProps: dispatch => ({
    fetchUsers: () => dispatch(setUsers()),
  }),
};

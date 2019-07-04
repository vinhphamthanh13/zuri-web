import React, { PureComponent } from 'react';
import { FullLoading, Cycle } from 'homecredit-ui';
import { bool } from 'prop-types';
import { compose } from 'redux';
import { connect } from 'react-redux';

class Loading extends PureComponent {
  static propTypes = {
    loading: bool,
  };

  static defaultProps = {
    loading: false,
  };

  render() {
    const FullLoadingCycle = FullLoading(Cycle);
    const { loading } = this.props;
    return <div>{loading && <FullLoadingCycle fullPage />}</div>;
  }
}

function mapStateToProps({ internal }) {
  const { loading } = internal;
  return { loading };
}

const enhance = compose(connect(mapStateToProps));

export default enhance(Loading);

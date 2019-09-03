import React, { Component } from 'react';
import { bool } from 'prop-types';
import { compose } from 'redux';
import { connect } from 'react-redux';
import { PulseLoader } from 'react-spinners';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { brand04 } from 'constants/colors';
import s from './Loading.css';

class Loading extends Component {
  static propTypes = {
    loading: bool,
  };

  static defaultProps = {
    loading: null,
  };

  state = {};

  render() {
    const { loading } = this.props;
    return (
      loading && (
        <div className={s.container}>
          <PulseLoader color={brand04} size={20} sizeUnit="px" loading />
        </div>
      )
    );
  }
}

const mapStateToProps = ({ common }) => ({
  loading: common.isLoading,
});

const enhancers = [withStyles(s), connect(mapStateToProps)];
export default compose(...enhancers)(Loading);

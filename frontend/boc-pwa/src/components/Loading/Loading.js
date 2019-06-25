import React from 'react';
import { bool } from 'prop-types';
const Loading = props => {
    const { isLoading } = props;
    return isLoading && <div>Loading</div>
};

Loading.propTypes = {
    isLoading: bool,
};

Loading.defaultProps = {
    isLoading: true,
};

export default Loading;

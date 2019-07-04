import React, { Component } from 'react';
import { hasGetUserMedia } from 'utils/browser';
import ImageField from './ImageField';
import ImageCapture from './ImageCapture';

export default class ImageInput extends Component {
  render() {
    return hasGetUserMedia() ? (
      <ImageCapture {...this.props} />
    ) : (
      <ImageField {...this.props} />
    );
  }
}

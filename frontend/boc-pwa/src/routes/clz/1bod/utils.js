import { filter, isEmpty, reduce, maxBy, minBy, get, map } from 'lodash';
import { IMAGE_MAX_SIZE } from 'constants/clz';
import { TENOR_BUTTONS } from './constant';

/**
 * Get Best Offer with lowest annual interest rate
 *
 * @param  {Array}  offerList Offer list
 * @param  {Number} tenor     Current tenor
 * @return {Object} The best offer object to store in redux
 */
export const getBestOffer = (offerList, tenor) => {
  const matchOffers = filter(
    offerList,
    offer => offer.celOffer.tenor === tenor,
  );

  // Can not find match offer
  if (isEmpty(matchOffers)) {
    return null;
  }

  return reduce(
    matchOffers,
    (l, e) =>
      e.celOffer.annualInterestRate < l.celOffer.annualInterestRate ? e : l,
  );
};

/**
 * Get min & max tenor in offer list to show in slider
 *
 * @param  {Array}  offerList Offer list
 * @return {Object} The best offer object to store in redux
 */
export const getOfferRange = offerList => {
  const max = maxBy(offerList, o => o.celOffer.tenor);
  const min = minBy(offerList, o => o.celOffer.tenor);

  return { max: get(max, 'celOffer.tenor'), min: get(min, 'celOffer.tenor') };
};

/**
 * Get list tenor in offer list to show in group button
 *
 * @param  {Array}  offerList Offer list
 * @return {Object} The best offer object to store in redux
 */
export const getOfferTenors = offerList => {
  const tenorValues = map(offerList, 'celOffer.tenor');

  return filter(
    TENOR_BUTTONS,
    tenor => tenorValues.indexOf(tenor.value) !== -1,
  );
};

/**
 * Get image size
 *
 * @param  {Base64}  image Image
 * @return {Object} Promise to resolve image size
 */
export const getSizeImage = image => {
  const stringLength = image.length - 'data:image/png;base64,'.length;
  const sizeInBytes = 4 * Math.ceil(stringLength / 3) * 0.5624896334383812;

  return sizeInBytes / 1000;
};

/**
 * Compress the image, maximum allow is 500KB
 *
 * @param  {Base64} images Image base64
 * @return {Object} Compressed image
 */
export const compressImage = images => {
  let quality = 1;
  const image = images[Object.keys(images)[0]];
  const sizeImg = getSizeImage(image);

  if (sizeImg > IMAGE_MAX_SIZE) {
    quality = IMAGE_MAX_SIZE / sizeImg;
  }

  return image
    .toDataURL('image/png', quality)
    .split(',')
    .pop();
};

import Compressor from 'compressorjs';

const MAX_SIZE = 400000;

function Compress(file) {
  const quality = file.size > MAX_SIZE ? MAX_SIZE / file.size : 1;

  return new Promise(resolve => {
    // eslint-disable-next-line
    new Compressor(file, {
      quality,
      success(result) {
        resolve(result);
      },
    });
  });
}

function dataURItoBlob(dataURI) {
  const byteString = atob(dataURI.split(',')[1]);

  const mimeString = dataURI
    .split(',')[0]
    .split(':')[1]
    .split(';')[0];

  const ab = new ArrayBuffer(byteString.length);
  const ia = new Uint8Array(ab);
  for (let i = 0; i < byteString.length; i += 1) {
    ia[i] = byteString.charCodeAt(i);
  }
  return new Blob([ab], { type: mimeString });
}

export function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

export async function imageCompression(imageUrl) {
  const blob = dataURItoBlob(imageUrl);
  const imageCompressed = await Compress(blob);
  const imageSource = await getBase64(imageCompressed);

  return imageSource;
}

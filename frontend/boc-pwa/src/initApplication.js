import storage from 'node-persist';
import initToken from './api/login';

export async function initNodeStorage() {
  await storage.init({
    dir: 'persist',

    stringify: JSON.stringify,

    parse: JSON.parse,

    encoding: 'utf8',

    logging: false, // can also be custom logging function

    ttl: false, // ttl* [NEW], can be true for 24h default or a number in MILLISECONDS or a valid Javascript Date object

    expiredInterval: 2 * 60 * 1000, // every 2 minutes the process will clean-up the expired cache

    // in some cases, you (or some other service) might add non-valid storage files to your
    // storage dir, i.e. Google Drive, make this true if you'd like to ignore these files and not throw an error
    forgiveParseErrors: false,
  });
}

export async function initApplication() {
  // Get first token
  const token = await initToken();
  if (token && token.expires_in) {
    // Refresh token
    setInterval(async () => {
      const newToken = await initToken();

      token && (await storage.setItem('clz_token', newToken.access_token));

      console.info('Refresh new token : ', newToken.access_token);
    }, 1800 * 1000);

    await storage.setItem('clz_token', token.access_token);
  } else {
    console.error('Failed to get Open API Token');
  }
}

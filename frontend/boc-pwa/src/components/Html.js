/**
 * BOC VN (http://www.bocvietnam.com/)
 *
 * Copyright © 2019-present BOCVN, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import PropTypes from 'prop-types';
import serialize from 'serialize-javascript';
import queryString from 'query-string';
import config from '../config';

/* eslint-disable react/no-danger */

class Html extends React.Component {
  static propTypes = {
    title: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    styles: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.string.isRequired,
        cssText: PropTypes.string.isRequired,
      }).isRequired,
    ),
    scripts: PropTypes.arrayOf(PropTypes.string.isRequired),
    // eslint-disable-next-line react/forbid-prop-types
    app: PropTypes.object.isRequired,
    children: PropTypes.string.isRequired,
  };

  static defaultProps = {
    styles: [],
    scripts: [],
  };

  render() {
    const { title, description, styles, scripts, app, children } = this.props;
    const { threatMetrix } = config;
    const params = queryString.stringify({
      org_id: threatMetrix.id,
      session_id: app.sessionID,
      pageid: 'acl',
    });

    return (
      <html className="no-js" lang={app.lang}>
        <head>
          <meta charSet="utf-8" />
          <meta httpEquiv="x-ua-compatible" content="ie=edge" />
          <title>{title}</title>
          <meta name="description" content={description} />
          <meta
            name="viewport"
            content="width=device-width, initial-scale=1, maximum-scale=1"
          />
          {scripts.map(script => (
            <link key={script} rel="preload" href={script} as="script" />
          ))}
          <link rel="manifest" href="/site.manifest" />
          <meta name="apple-mobile-web-app-capable" content="yes" />
          <meta name="apple-mobile-web-app-status-bar-style" content="black" />
          <meta name="apple-mobile-web-app-title" content="BOCVN" />
          <link
            rel="apple-touch-icon"
            href="../../public/images/boc_logo_48.png"
          />
          <link
            rel="apple-touch-icon"
            href="../../public/images/boc_logo_96.png"
          />
          <link
            rel="apple-touch-icon"
            href="../../public/images/boc_logo_144.png"
          />
          <link
            rel="apple-touch-icon"
            href="../../public/images/boc_logo_192.png"
          />
          <link
            rel="apple-touch-icon"
            href="../../public/images/boc_logo_256.png"
          />
          <link
            rel="apple-touch-icon"
            href="../../public/images/boc_logo_384.png"
          />
          <link
            rel="apple-touch-icon"
            href="../../public/images/boc_logo_512.png"
          />
          <meta
            name="msapplication-TileImage"
            content="../../public/images/boc_logo_48.png"
          />
          <meta name="msapplication-TileColor" content="#fff" />
          <meta name="theme-color" content="#00AEEF" />
          <link
            href="https://fonts.googleapis.com/css?family=Roboto:400,500,500i&display=swap&subset=vietnamese"
            rel="stylesheet"
          />
          <link
            href="https://fonts.googleapis.com/icon?family=Material+Icons"
            rel="stylesheet"
          />
          {styles.map(style => (
            <style
              key={style.id}
              id={style.id}
              dangerouslySetInnerHTML={{ __html: style.cssText }}
            />
          ))}
          <script src="https://sdk.accountkit.com/en_US/sdk.js" />
        </head>
        <body>
          <div id="app" dangerouslySetInnerHTML={{ __html: children }} />
          <script
            dangerouslySetInnerHTML={{ __html: `window.App=${serialize(app)}` }}
          />
          {scripts.map(script => <script key={script} src={script} />)}
          {config.analytics.googleTrackingId && (
            <script
              dangerouslySetInnerHTML={{
                __html:
                  'window.ga=function(){ga.q.push(arguments)};ga.q=[];ga.l=+new Date;' +
                  `ga('create','${
                    config.analytics.googleTrackingId
                  }','auto');ga('send','pageview')`,
              }}
            />
          )}
          {config.analytics.googleTrackingId && (
            <script
              src="https://www.google-analytics.com/analytics.js"
              async
              defer
            />
          )}
          <script
            type="text/javascript"
            src={`${threatMetrix.scriptLink}?${params}`}
          />
          <noscript>
            <iframe
              title="threatMetrix"
              style={{
                width: 100,
                height: 100,
                border: 0,
                position: 'absolute',
                top: -5000,
              }}
              src={`${threatMetrix.embedLink}?${params}`}
            />
          </noscript>
          <input id="sessionID" value={app.sessionID} type="hidden" />
        </body>
      </html>
    );
  }
}

export default Html;

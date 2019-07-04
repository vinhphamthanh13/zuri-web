import React, { PureComponent, Fragment } from 'react';
import { Input, Button, FullLoading, Cycle } from 'homecredit-ui';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import { withFormik } from 'formik';
import { compose } from 'redux';
import { getValidationSchema } from 'utils/validations';
import noop from 'lodash/noop';
import { func, shape, bool, string } from 'prop-types';
import classnames from 'classnames';
import QRCode from 'qrcode-react';
import html2canvas from 'html2canvas';
import { generateShortURL } from 'actions/shortURL';
import { verifySACode } from 'actions/sale';

import {
  VERIFY,
  VERIFIED,
  GENERATED_LINK,
  GENERATE_LINK,
  SA_PLACEHOLDER,
  SA_TITLE,
  INVALIDATE_SA,
  QR_SLOGAN,
  QR_HELPER_1,
  QR_HELPER_2,
  SA_NAME_LABEL,
  SA_PHONE_LABEL,
  DOWNLOAD,
  PRINTING,
} from './constant';
import s from './GenerateLink.css';
import linkQR from './images/linkQR.png';

const FullLoadingCycle = FullLoading(Cycle);

class GenerateLink extends PureComponent {
  static propTypes = {
    values: shape({
      saCode: string,
      isVerity: bool,
      shortLink: string,
    }),
    touched: shape({
      saCode: bool,
      isVerity: bool,
      shortLink: bool,
    }),
    errors: shape({
      saCode: string,
      isVerity: string,
      shortLink: string,
    }),
    setErrors: func,
    handleChange: func,
    setValues: func,
    handleBlur: func,
    setTouched: func,
  };

  static defaultProps = {
    values: {},
    touched: {},
    errors: {},
    setErrors: noop,
    handleChange: noop,
    setValues: noop,
    handleBlur: noop,
    setTouched: noop,
  };

  state = {
    shortLink: '',
    isVerity: false,
    loading: false,
  };

  qrCanvas = React.createRef();

  /**
   * handleVeritySaCode handle
   * when user click to Verity button
   * @return {void}
   */
  handleVeritySaCode = async () => {
    const { setTouched, setErrors, touched, validateForm } = this.props;
    const { saCode } = this.props.values;

    if (!touched.saCode) {
      setTouched({ saCode: true });
    }

    if (!saCode) {
      validateForm();
      return;
    }

    this.setState(() => ({
      loading: true,
    }));
    const saCodeResult = await verifySACode(saCode);
    this.setState(() => ({
      loading: false,
    }));
    if (saCodeResult.valid) {
      const { phoneNumber, name } = saCodeResult;
      this.setState(() => ({
        isVerity: true,
        saName: name,
        saPhone: phoneNumber,
      }));
    } else {
      setErrors({ saCode: INVALIDATE_SA });
    }
  };

  /**
   * Handle when user click to GENERATE_LINK button
   * @return {void} {description}
   */
  handleGenerateQRCode = async () => {
    const { saCode } = this.props.values;
    const { shortId } = await generateShortURL(saCode);
    this.setState(() => ({
      shortLink: shortId,
    }));
  };

  handleChange = e => {
    const { handleChange, setValues } = this.props;
    const { shortLink, isVerity } = this.state;

    handleChange(e);

    if (shortLink || isVerity) {
      this.setState(() => ({
        shortLink: '',
        isVerity: false,
      }));
      setValues({
        saCode: e.target.value,
      });
    }
  };

  handleLinkClick = e => {
    e.preventDefault();
  };

  /**
   *  Render the QR with background and SA info
   *  @return {object}
   */

  handleDownloadSAQR = () => {
    const windowRef = window.open;
    this.setState(
      {
        loading: true,
      },
      () =>
        html2canvas(this.qrCanvas.current)
          .then(canvas => {
            windowRef().document.write(
              `<img src="${canvas.toDataURL('image/png')}" alt="SA QR code" />`,
            );
            this.setState({
              loading: false,
            });
          })
          .catch(() => {
            this.setState({ loading: false });
          }),
    );
  };

  handlePrintSAQR = () => {
    window.print();
  };

  renderQR = link => {
    const { saName, saPhone } = this.state;

    return (
      <>
        <div className={s.qrCode} ref={this.qrCanvas}>
          <div>
            <img src={linkQR} alt="QR SA code" className={s.qrImage} />
          </div>
          <div className={s.qrInfo}>
            <div className={s.helpText1}>{QR_SLOGAN}</div>
            <div className={s.saQrCode}>
              <QRCode value={link} logo="/images/logo_qr.png" size={86} />
            </div>
            <div className={s.helpText2}>{QR_HELPER_1}</div>
            <div className={s.saInfo}>
              <div className={`${s.saDetail} ${s.saName}`}>
                {SA_NAME_LABEL}: <strong>{saName}</strong>
              </div>
              <div className={s.saDetail}>
                {SA_PHONE_LABEL}: <strong>{saPhone}</strong>
              </div>
            </div>
            <div className={s.helpText3}>{` ${QR_HELPER_2} `}</div>
          </div>
        </div>
        <div className={s.qrButtons}>
          <Button className={s.qrButton} onClick={this.handleDownloadSAQR}>
            {DOWNLOAD}
          </Button>
          <Button
            className={`${s.qrButton} ${s.qrPrintButton}`}
            onClick={this.handlePrintSAQR}
          >
            {PRINTING}
          </Button>
        </div>
      </>
    );
  };

  render() {
    const { touched, errors, handleBlur } = this.props;
    const { saCode } = this.props.values;
    const { shortLink, isVerity, loading } = this.state;
    const link = `${window.location.origin}/${shortLink}`;
    return (
      <div className={s.container}>
        <h1 className={s.title}>{SA_TITLE}</h1>
        <Input
          label={SA_PLACEHOLDER}
          name="saCode"
          value={saCode}
          onChange={this.handleChange}
          onBlur={handleBlur}
          touched={(errors.saCode && touched.saCode) || isVerity}
          errorMsg={errors.saCode}
          className={s.inputSa}
        />
        <div className={s.groupBtn}>
          <Button
            className={classnames(s.btnRest, s.btnLeft)}
            disabled={isVerity}
            onClick={this.handleVeritySaCode}
          >
            {isVerity ? VERIFIED : VERIFY}
          </Button>
          <Button
            className={s.btnRest}
            disabled={!isVerity || !!shortLink}
            onClick={this.handleGenerateQRCode}
          >
            {shortLink ? GENERATED_LINK : GENERATE_LINK}
          </Button>
          {shortLink && (
            <Fragment>
              <div className={s.shortLink}>
                <a href={link} onClick={this.handleLinkClick}>
                  {link}
                </a>
              </div>
            </Fragment>
          )}
        </div>
        {shortLink && this.renderQR(link)}
        {loading && <FullLoadingCycle fullPage />}
      </div>
    );
  }
}

export default compose(
  withStyles(s),
  withFormik({
    mapPropsToValues: () => ({
      saCode: '',
    }),
    validationSchema: getValidationSchema(['saCode']),
  }),
)(GenerateLink);

class SaleModel {
  constructor(config) {
    const {
      name = null,
      code = null,
      shopCode = null,
      mail = null,
      dsmMail = null,
      phoneNumber = null,
      createAt = new Date().getTime(),
      updateAt = null,
    } = config;

    this.name = name;
    this.code = code;
    this.shopCode = shopCode;
    this.mail = mail;
    this.dsmMail = dsmMail;
    this.phoneNumber = phoneNumber;
    this.createAt = createAt;
    this.updateAt = updateAt;
  }
}

export default SaleModel;

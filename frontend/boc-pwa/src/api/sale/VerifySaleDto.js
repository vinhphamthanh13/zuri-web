class VerifySaleDto {
  constructor(config) {
    const {
      isValid = false,
      name = null,
      phoneNumber = null,
      mail = null,
    } = config;

    this.isValid = isValid;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.mail = mail;
  }
}

export default VerifySaleDto;

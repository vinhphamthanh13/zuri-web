class TokenDto {
  constructor(config) {
    const {
      user = null,
      token = null,
      tokenType = 'bearer',
      expiresIn = null,
    } = config;

    this.user = user;
    this.token = token;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
  }
}

export default TokenDto;

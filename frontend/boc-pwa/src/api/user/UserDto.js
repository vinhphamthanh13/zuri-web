class UserDto {
  constructor(config) {
    const { name = null, username = null, roles = [], mail = null } = config;

    this.name = name;
    this.username = username;
    this.roles = roles;
    this.mail = mail;
  }
}

export default UserDto;

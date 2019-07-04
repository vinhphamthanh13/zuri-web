class UserModel {
  constructor(config) {
    const {
      name = null,
      username = null,
      password = null,
      roles = [],
      mail = null,
      isActive = true,
      createAt = new Date().getTime(),
      updateAt = null,
    } = config;

    this.name = name;
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.mail = mail;
    this.isActive = isActive;
    this.createAt = createAt;
    this.updateAt = updateAt;
  }
}

export default UserModel;

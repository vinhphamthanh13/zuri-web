import fetch from 'node-fetch';

import { HTTP_STATUS, CONTENT_TYPE } from 'constants/http';
import ResponseDto from '../ResponseDto';
import config from '../../config';
import UserModel from './UserModel';
import TokenDto from './TokenDto';
import UserDto from './UserDto';

const { internalAuth } = config.auth;
const { javaAPI } = config.api;
const InternalUserName = 'internalUser';

class UserService {
  constructor(db) {
    this.db = db;
  }

  static async login(username, plainPassword) {
    const response = await fetch(`${javaAPI}/v1.0/token`, {
      method: 'POST',
      headers: {
        'Content-Type': CONTENT_TYPE.JSON,
      },
      body: JSON.stringify({
        username,
        password: plainPassword,
      }),
    });
    const { data } = await response.json();

    if (data) {
      const { token, expiresIn, user } = data;
      return new ResponseDto(
        new TokenDto({
          user: new UserDto({ name: user, username: user }),
          token,
          expiresIn: expiresIn + Date.now(),
        }),
      );
    }

    return new ResponseDto(null, HTTP_STATUS.UNAUTHORIZED);
  }

  async checkAndAddingAdminUser() {
    const users = this.db.collection(InternalUserName);
    const adminUser = await users.findOne({ username: internalAuth.adminUser });
    if (!adminUser) {
      const user = new UserModel({
        name: internalAuth.adminUser,
        username: internalAuth.adminUser,
        password: internalAuth.adminHashPassword,
        roles: ['admin'],
      });

      await users.insertOne(user);
    }
  }
}

export default UserService;

package com.ocha.boc.repository;

import com.ocha.boc.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findUserByPhone(String phone);
    User findUserById(String id);
}

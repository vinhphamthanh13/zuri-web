package com.ocha.boc.repository;

import com.ocha.boc.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByPhone(String phone);
}

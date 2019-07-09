package com.ocha.boc.repository;

import com.ocha.boc.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findUserByPhone(String phone);
    User findUserById(String id);

    @Query(value = "{'isActive' : true}")
    List<User> getListUserActiveIsTrue();
}

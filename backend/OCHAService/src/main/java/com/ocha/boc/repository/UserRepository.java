package com.ocha.boc.repository;

import com.ocha.boc.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByPhone(String phone);

    Optional<User> findUserByPhoneAndIsActive(String phone, boolean isActive);

    Optional<User> findUserById(String id);

    @Query(value = "{'isActive' : true}")
    List<User> getListUserActiveIsTrue();
}

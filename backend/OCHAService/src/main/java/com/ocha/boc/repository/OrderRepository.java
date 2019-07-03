package com.ocha.boc.repository;

import com.ocha.boc.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String > {

    @Query(value = "{'createDate': ?0}")
    List<Order> findListOrderByCreateDate(String date);
}

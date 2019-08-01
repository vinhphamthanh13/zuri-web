package com.ocha.boc.repository;

import com.ocha.boc.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String > {
}

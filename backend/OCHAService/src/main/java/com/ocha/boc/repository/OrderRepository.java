package com.ocha.boc.repository;

import com.ocha.boc.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    Order findOrderByIdAndCuaHangId(String orderId, String cuaHangId);

    Order findOrderByReceiptCodeAndCuaHangId(String receiptCode, String cuaHangId);

    Order findOrderByTakeAWayOptionCodeAndCuaHangId(String takeAWayCode, String cuaHangId);
}

package com.ocha.boc.repository;

import com.ocha.boc.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    Order findOrderByIdAndCuaHangId(String orderId, String cuaHangId);

    Order findOrderByReceiptCodeAndCuaHangId(String receiptCode, String cuaHangId);

    Order findOrderByTakeAWayOptionCodeAndCuaHangId(String takeAWayCode, String cuaHangId);

    List<Order> findAllOrderByCuaHangId(String cuaHangId);

    List<Order> findAllOrderByCreatedDateAndCuaHangId(String createDate, String cuaHangId);

    @Query(value = "{'cuaHangId': ?0 , 'createdDate' : {$gte: ?1, $lte: ?2}}")
    List<Order> findAllOrderByCuaHangIdCreateDateBetween(String cuaHangId ,String fromDate, String toDate);
}

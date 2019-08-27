package com.ocha.boc.repository;

import com.ocha.boc.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findOrderByIdAndCuaHangId(String orderId, String cuaHangId);

    Optional<Order> findOrderByReceiptCodeAndCuaHangId(String receiptCode, String cuaHangId);

    Optional<Order> findOrderByTakeAWayOptionCodeAndCuaHangId(String takeAWayCode, String cuaHangId);

    Page<Order> findAllByCuaHangId(Pageable pageable, String cuaHangId);

//    List<Order> findAllOrderByCuaHangId(String cuaHangId);

    List<Order> findAllOrderByCreatedDateAndCuaHangId(String createDate, String cuaHangId);

    @Query(value = "{'cuaHangId': ?0 , 'createdDate' : {$gte: ?1, $lte: ?2}}")
    List<Order> findAllOrderByCuaHangIdCreateDateBetween(String cuaHangId ,String fromDate, String toDate);
}

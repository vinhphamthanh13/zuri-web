package com.ocha.boc.repository;

import com.ocha.boc.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findOrderByIdAndRestaurantId(String orderId, String restaurantId);

    Optional<Order> findOrderByReceiptCodeAndRestaurantId(String receiptCode, String restaurantId);

    Optional<Order> findOrderByTakeAWayOptionCodeAndRestaurantId(String takeAWayCode, String restaurantId);

    Page<Order> findAllByRestaurantId(Pageable pageable, String restaurantId);

//    List<Order> findAllOrderByCuaHangId(String cuaHangId);

    List<Order> findAllOrderByCreatedDateAndRestaurantId(String createDate, String restaurantId);

    @Query(value = "{'cuaHangId': ?0 , 'createdDate' : {$gte: ?1, $lte: ?2}}")
    List<Order> findAllOrderByCuaHangIdCreateDateBetween(String cuaHangId ,String fromDate, String toDate);
}

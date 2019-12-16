package com.ocha.boc.repository;

import com.ocha.boc.entity.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {


    Optional<Restaurant> findRestaurantById(String id);

    Optional<Restaurant> findRestaurantByAddressAndRestaurantName(String address, String cuaHangName);

    boolean existsByAddressAndRestaurantName(String address, String cuaHangName);

    boolean existsById(String id);
}

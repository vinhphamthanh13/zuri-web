package com.ocha.boc.repository;

import com.ocha.boc.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String>,
        PagingAndSortingRepository<Product, String>, ProductRepositoryCustom {

    Optional<Product> findProductByIdAndRestaurantId(String id, String restaurantId);

    List<Product> findAllByRestaurantId(String restaurantId);

    boolean existsByNameAndRestaurantId(String name, String restaurantId);

    boolean existsByIdAndRestaurantId(String id, String restaurantId);
}

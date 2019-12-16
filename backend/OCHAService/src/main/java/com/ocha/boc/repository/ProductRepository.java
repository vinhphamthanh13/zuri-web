package com.ocha.boc.repository;

import com.ocha.boc.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String>,
        PagingAndSortingRepository<Product, String>, RepositoryCustom {

    Optional<Product> findProductByIdAndRestaurantId(String id, String restaurantId);

    boolean existsByNameAndRestaurantId(String name, String restaurantId);

    boolean existsByIdAndRestaurantId(String id, String restaurantId);
}

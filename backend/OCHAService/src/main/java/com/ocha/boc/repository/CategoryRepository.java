package com.ocha.boc.repository;

import com.ocha.boc.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findCategoryByName(String name);

    Optional<Category> findCategoryByCategoryIdAndRestaurantId(String categoryId, String restaurantId);

    Optional<Category> findTopByOrderByCreatedDateDesc();

    List<Category> findAllByRestaurantId(String restaurantId);

    boolean existsByName(String name);

    boolean existsByCategoryIdAndRestaurantId(String categoryId, String restaurantId);
}

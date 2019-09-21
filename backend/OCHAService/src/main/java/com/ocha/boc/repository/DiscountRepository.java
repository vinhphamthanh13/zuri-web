package com.ocha.boc.repository;

import com.ocha.boc.entity.Discount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DiscountRepository extends MongoRepository<Discount, String> {

    Optional<Discount> findDiscountById(String discountId);

    boolean existsById(String discountId);
}

package com.ocha.boc.repository;

import com.ocha.boc.entity.ProductPortfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductPortfolioRepository extends MongoRepository<ProductPortfolio, String> {
}

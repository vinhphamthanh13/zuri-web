package com.ocha.boc.repository;

import com.ocha.boc.entity.Product;
import com.ocha.boc.request.ProductListRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;


public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Product> query(ProductListRequest request, Pageable pageable) {
        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();
        if (StringUtils.isNotEmpty(request.getRestaurantId())) {
            criteria.add(Criteria.where("restaurantId").is(request.getRestaurantId()));
        }
        if (StringUtils.isNotEmpty(request.getName())) {
            criteria.add(Criteria.where("name").regex(request.getName(), "i"));
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        Page<Product> result = new PageImpl<Product>(mongoTemplate.find(query, Product.class), pageable,
                mongoTemplate.find(query, Product.class).size());
        return result;
    }


}

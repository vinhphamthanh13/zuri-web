package com.ocha.boc.repository;

import com.ocha.boc.entity.MatHang;

import com.ocha.boc.repository.MatHangRepositoryCustom;
import com.ocha.boc.request.MatHangListRequest;
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


public class MatHangRepositoryImpl implements MatHangRepositoryCustom {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public MatHangRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<MatHang> query(MatHangListRequest request, Pageable pageable) {
        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();
        if (StringUtils.isNotEmpty(request.getCuaHangId())) {
            criteria.add(Criteria.where("cuaHangId").is(request.getCuaHangId()));
        }
        if (StringUtils.isNotEmpty(request.getName())) {
            criteria.add(Criteria.where("name").regex( request.getName() , "i"));
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        Page<MatHang> result = new PageImpl<MatHang>(mongoTemplate.find(query, MatHang.class), pageable,
                mongoTemplate.find(query, MatHang.class).size());
        return result;
    }


}

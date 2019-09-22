package com.ocha.boc.repository;

import com.ocha.boc.entity.BusinessModelsType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessModelsTypeRepository extends MongoRepository<BusinessModelsType, String> {
}

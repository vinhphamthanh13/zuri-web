package com.ocha.boc.repository;

import com.ocha.boc.entity.SampleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleRepository extends MongoRepository<SampleEntity, String>  {
}

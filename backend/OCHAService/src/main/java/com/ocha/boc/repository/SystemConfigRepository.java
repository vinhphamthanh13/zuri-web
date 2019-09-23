package com.ocha.boc.repository;

import com.ocha.boc.entity.SystemConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemConfigRepository extends MongoRepository<SystemConfig, String> {


}

package com.ocha.boc.repository;

import com.ocha.boc.entity.GiayIn;
import com.ocha.boc.entity.SystemConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SystemConfigRepository extends MongoRepository<SystemConfig, String > {

    List<GiayIn> findAllByListGiayInConfiguration();
}

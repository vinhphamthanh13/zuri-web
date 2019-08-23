package com.ocha.boc.repository;

import com.ocha.boc.entity.MoHinhKinhDoanh;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoHinhKinhDoanhRepository extends MongoRepository<MoHinhKinhDoanh, String> {
}

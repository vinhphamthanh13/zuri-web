package com.ocha.boc.repository;

import com.ocha.boc.entity.DanhMuc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DanhMucRepository extends MongoRepository<DanhMuc, String> {

    DanhMuc findDanhMucByName(String name);

    DanhMuc findDanhMucByDanhMucId(String danhMucId);

    DanhMuc findTopByOrderByDanhMucIdDesc();
}

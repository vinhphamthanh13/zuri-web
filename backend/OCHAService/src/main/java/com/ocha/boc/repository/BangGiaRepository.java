package com.ocha.boc.repository;

import com.ocha.boc.entity.BangGia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BangGiaRepository extends MongoRepository<BangGia, String> {

    BangGia findBangGiaByBangGiaId(String id);

    BangGia findTopByOrderByBangGiaIdDesc();

}

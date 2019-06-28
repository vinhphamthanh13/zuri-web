package com.ocha.boc.repository;

import com.ocha.boc.entity.BangGiaDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BangGiaDetailRepository extends MongoRepository<BangGiaDetail, String> {

    BangGiaDetail findBangGiaDetailById(String id);

    List<BangGiaDetail> findBangGiaDetailByBangGiaId(String bangGiaId);

    Integer countBangGiaDetailByBangGiaId(String bangGiaId);
}

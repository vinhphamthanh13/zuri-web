package com.ocha.boc.repository;

import com.ocha.boc.entity.GiamGia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GiamGiaRepository extends MongoRepository<GiamGia, String> {

    GiamGia findGiamGiaById(String giamGiaId);
}

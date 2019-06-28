package com.ocha.boc.repository;

import com.ocha.boc.entity.NguyenLieu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NguyenLieuRepository extends MongoRepository<NguyenLieu, String> {

    NguyenLieu findNguyenLieuByName(String name);

    NguyenLieu findNguyenLieuById(String id);
}

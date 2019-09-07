package com.ocha.boc.repository;

import com.ocha.boc.entity.NguyenLieu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NguyenLieuRepository extends MongoRepository<NguyenLieu, String> {

    Optional<NguyenLieu> findNguyenLieuByName(String name);

    Optional<NguyenLieu> findNguyenLieuById(String id);

    boolean existsByName(String name);

    boolean existsById(String id);
}

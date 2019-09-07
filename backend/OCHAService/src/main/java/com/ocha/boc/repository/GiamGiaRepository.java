package com.ocha.boc.repository;

import com.ocha.boc.entity.GiamGia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GiamGiaRepository extends MongoRepository<GiamGia, String> {

    Optional<GiamGia> findGiamGiaById(String giamGiaId);

    boolean existsById(String giamGiaId);
}

package com.ocha.boc.repository;

import com.ocha.boc.entity.DanhMuc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DanhMucRepository extends MongoRepository<DanhMuc, String> {

    Optional<DanhMuc> findDanhMucByName(String name);

    Optional<DanhMuc> findDanhMucByDanhMucIdAndCuaHangId(String danhMucId, String cuaHangId);

    Optional<DanhMuc> findTopByOrderByDanhMucIdDesc();

    List<DanhMuc> findAllByCuaHangId(String cuaHangId);

    boolean existsByName(String name);

    boolean existsByDanhMucIdAndCuaHangId(String danhMucId, String cuaHangId);
}

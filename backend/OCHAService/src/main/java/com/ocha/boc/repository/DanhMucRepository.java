package com.ocha.boc.repository;

import com.ocha.boc.entity.DanhMuc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DanhMucRepository extends MongoRepository<DanhMuc, String> {

    DanhMuc findDanhMucByName(String name);

    DanhMuc findDanhMucByDanhMucIdAndCuaHangId(String danhMucId, String cuaHangId);

    DanhMuc findTopByOrderByDanhMucIdDesc();

    List<DanhMuc> findAllByCuaHangId(String cuaHangId);
}

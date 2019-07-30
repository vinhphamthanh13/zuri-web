package com.ocha.boc.repository;

import com.ocha.boc.entity.CuaHang;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CuaHangRepository extends MongoRepository<CuaHang, String > {
    CuaHang findCuaHangByPhone(String phone);

    CuaHang findCuaHangById(String id);

    CuaHang findTopByOrderByCreatedDateDesc();
}

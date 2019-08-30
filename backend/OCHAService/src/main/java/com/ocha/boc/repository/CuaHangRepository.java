package com.ocha.boc.repository;

import com.ocha.boc.entity.CuaHang;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CuaHangRepository extends MongoRepository<CuaHang, String > {
    CuaHang findCuaHangByPhone(String phone);

    CuaHang findCuaHangById(String id);

    CuaHang findTopByOrderByCreatedDateDesc();

    Optional<CuaHang> findCuaHangByAddressAndCuaHangName(String address, String cuaHangName);
}

package com.ocha.boc.repository;

import com.ocha.boc.entity.CuaHang;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CuaHangRepository extends MongoRepository<CuaHang, String> {
    Optional<CuaHang> findCuaHangByPhone(String phone);

    Optional<CuaHang> findCuaHangById(String id);

    CuaHang findTopByOrderByCreatedDateDesc();

    Optional<CuaHang> findCuaHangByAddressAndCuaHangName(String address, String cuaHangName);

    boolean existsByAddressAndCuaHangName(String address, String cuaHangName);

    boolean existsById(String id);
}

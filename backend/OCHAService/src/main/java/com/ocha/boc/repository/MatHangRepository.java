package com.ocha.boc.repository;

import com.ocha.boc.entity.MatHang;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MatHangRepository extends MongoRepository<MatHang, String > {

    Optional<MatHang> findMatHangByNameAndCuaHangId (String name, String cuaHangId);

    Optional<MatHang> findMatHangByIdAndCuaHangId(String id, String cuaHangId);

    List<MatHang> findAllByCuaHangId(String cuaHangId);
}

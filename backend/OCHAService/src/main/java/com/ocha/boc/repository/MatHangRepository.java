package com.ocha.boc.repository;

import com.ocha.boc.entity.MatHang;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatHangRepository extends MongoRepository<MatHang, String > {

    MatHang findMatHangByNameAndCuaHangId (String name, String cuaHangId);

    MatHang findMatHangByIdAndCuaHangId(String id, String cuaHangId);

    List<MatHang> findAllByCuaHangId(String cuaHangId);
}

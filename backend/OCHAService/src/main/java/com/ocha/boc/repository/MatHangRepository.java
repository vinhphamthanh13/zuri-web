package com.ocha.boc.repository;

import com.ocha.boc.entity.MatHang;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface MatHangRepository extends MongoRepository<MatHang, String>,
        PagingAndSortingRepository<MatHang, String>, MatHangRepositoryCustom {

    Optional<MatHang> findMatHangByNameAndCuaHangId(String name, String cuaHangId);

    Optional<MatHang> findMatHangByIdAndCuaHangId(String id, String cuaHangId);

    List<MatHang> findAllByCuaHangId(String cuaHangId);

    boolean existsByNameAndCuaHangId(String name, String cuaHangId);

    boolean existsByIdAndCuaHangId(String id, String cuaHangId);
}

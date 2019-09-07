package com.ocha.boc.repository;

import com.ocha.boc.entity.NhanVien;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends MongoRepository<NhanVien, String> {

    Optional<NhanVien> findNhanVienByUsername(String username);

    List<NhanVien> findAllByCuaHangId(String cuaHangId);

    Optional<NhanVien> findNhanVienById(String nhanVienId);

    boolean existsByUsername(String username);

    boolean existsById(String nhanVienId);
}

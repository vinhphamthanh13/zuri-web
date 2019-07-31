package com.ocha.boc.repository;

import com.ocha.boc.entity.NhanVien;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NhanVienRepository extends MongoRepository<NhanVien , String > {

    NhanVien findNhanVienByUsername(String username);

    List<NhanVien> findAllByCuaHangId(String cuaHangId);

    NhanVien findNhanVienById(String nhanVienId);
}

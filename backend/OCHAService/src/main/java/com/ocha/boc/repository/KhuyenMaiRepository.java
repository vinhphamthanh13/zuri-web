package com.ocha.boc.repository;

import com.ocha.boc.entity.KhuyenMai;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface KhuyenMaiRepository extends MongoRepository<KhuyenMai, String> {

    KhuyenMai findKhuyenMaiByRateAndFromDateAndToDateAndCuaHangId(double rate, String fromDate, String toDate, String cuaHangId);

    KhuyenMai findTopByOrderByKhuyenMaiIdDesc();

    KhuyenMai findKhuyenMaiByKhuyenMaiIdAndCuaHangId(String khuyenMaiId, String cuaHangId);

    KhuyenMai findKhuyenMaiByKhuyenMaiId(String khuyenMaiId);

    List<KhuyenMai> findAllByCuaHangId(String cuaHangId);

}

package com.ocha.boc.repository;

import com.ocha.boc.entity.KhuyenMai;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KhuyenMaiRepository extends MongoRepository<KhuyenMai, String> {

    KhuyenMai findKhuyenMaiByRateAndFromDateAndToDate(double rate, String fromDate, String toDate);

    KhuyenMai findTopByOrderByKhuyenMaiIdDesc();

    KhuyenMai findKhuyenMaiByKhuyenMaiId(String khuyenMaiId);

}

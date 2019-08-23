package com.ocha.boc.repository;

import com.ocha.boc.entity.DanhMucSanPham;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DanhMucSanPhamRepository extends MongoRepository<DanhMucSanPham, String > {
}

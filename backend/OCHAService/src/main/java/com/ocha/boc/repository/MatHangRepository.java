package com.ocha.boc.repository;

import com.ocha.boc.entity.MatHang;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatHangRepository extends MongoRepository<MatHang, String > {

    MatHang findMatHangByName (String name);

    MatHang findMatHangById(String id);
}

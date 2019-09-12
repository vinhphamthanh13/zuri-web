package com.ocha.boc.repository;

import com.ocha.boc.entity.MatHang;
import com.ocha.boc.request.MatHangListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MatHangRepositoryCustom {
    Page<MatHang> query(MatHangListRequest request, Pageable pageable);
}

package com.ocha.boc.repository;

import com.ocha.boc.entity.Product;
import com.ocha.boc.request.ProductListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryCustom {
    Page<Product> query(ProductListRequest request, Pageable pageable);
}

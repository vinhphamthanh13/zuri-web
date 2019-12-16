package com.ocha.boc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCustom<PageRequest, A> {

    Page<A> query(PageRequest request, Pageable pageable);
}

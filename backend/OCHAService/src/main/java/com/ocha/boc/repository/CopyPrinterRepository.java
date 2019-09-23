package com.ocha.boc.repository;


import com.ocha.boc.entity.CopyPrinter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CopyPrinterRepository extends MongoRepository<CopyPrinter, String> {

    //List<CopyPrinter> findAllByListGiayInConfiguration();

    boolean existsByTitle(String title);

    Optional<CopyPrinter> findByTitle(String title);
}


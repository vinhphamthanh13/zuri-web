package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * NguyenLieu object means "nguyên liệu"
 */
@Getter
@Setter
@ToString
@Document(collection = NguyenLieu.COLLECTION_NAME)
public class NguyenLieu {

    public static final String COLLECTION_NAME = "nguyenlieu";

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String abbreviations;

    private String name;


}

package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * NguyenLieu object means "nguyên liệu"
 */
@Getter
@Setter
@ToString
@Document(collection = NguyenLieu.COLLECTION_NAME)
public class NguyenLieu extends AbstractEntity {

    public static final String COLLECTION_NAME = "nguyenlieu";

    private String abbreviations;

    private String name;



}

package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = DanhMuc.COLLECTION_NAME)
public class DanhMuc extends AbstractEntity {

    public static final String COLLECTION_NAME = "danhmuc";

    private String abbreviations;

    private String name;

    private String danhMucId;
}

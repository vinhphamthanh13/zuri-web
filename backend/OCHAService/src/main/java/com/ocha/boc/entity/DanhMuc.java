package com.ocha.boc.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = DanhMuc.COLLECTION_NAME)
@TypeAlias(value = "danhmuc")
public class DanhMuc implements Serializable {

    public static final String COLLECTION_NAME = "danhmuc";

    private static DanhMuc EMPTY = new DanhMuc();

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String abbreviations;

    private String name;

    private String danhMucId;

    private String cuaHangId;

    public boolean checkObjectEmptyData()
    {
        return this.equals(EMPTY);
    }
}

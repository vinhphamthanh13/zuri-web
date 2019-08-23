package com.ocha.boc.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = DanhMucSanPham.COLLECTION_NAME)
public class DanhMucSanPham {

    public static final String COLLECTION_NAME = "danhmucsanpham";

    private String name;
}

package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * MatHang Object means "Mặt hàng"
 */
@Getter
@Setter
@ToString
@Document(collection = MatHang.COLLECTION_NAME)
public class MatHang {

    public static final String COLLECTION_NAME = "mathang";

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String cuaHangId;

    private String name;

    private String danhMucId;

    private List<BangGia> listBangGia = new ArrayList<BangGia>();

}

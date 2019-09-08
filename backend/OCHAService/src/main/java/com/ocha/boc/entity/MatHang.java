package com.ocha.boc.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MatHang Object means "Mặt hàng"
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = MatHang.COLLECTION_NAME)
public class MatHang implements Serializable {

    public static final String COLLECTION_NAME = "mathang";

    private static MatHang EMPTY = new MatHang();

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String cuaHangId;

    private String name;

    private String danhMucId;

    private List<BangGia> listBangGia = new ArrayList<BangGia>();

    public boolean checkObjectEmptyData()
    {
        return this.equals(EMPTY);
    }

}

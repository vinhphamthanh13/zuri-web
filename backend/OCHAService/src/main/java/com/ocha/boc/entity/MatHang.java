package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
public class MatHang extends AbstractEntity {

    public static final String COLLECTION_NAME = "mathang";

    private String cuaHangId;

    private String name;

    private String danhMucId;

    private List<BangGia> listBangGia = new ArrayList<BangGia>();

    private String khuyenMaiId;
}

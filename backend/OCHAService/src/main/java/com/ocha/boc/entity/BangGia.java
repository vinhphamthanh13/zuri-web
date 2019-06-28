package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Bảng Giá Object
 */
@Getter
@Setter
@ToString
@Document(collection = BangGia.COLLECTION_NAME)
public class BangGia extends AbstractEntity {

    public static final String COLLECTION_NAME = "banggia";
    /**
     * Tổng số lượng giá
     */
    private int numberOfPrice;

    private String bangGiaId;
}

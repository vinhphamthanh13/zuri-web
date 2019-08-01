package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.enums.GiamGiaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Document(collection = GiamGia.COLLECTION_NAME)
public class GiamGia extends AbstractEntity {

    public static final String COLLECTION_NAME = "giamgia";

    private String cuaHangId;

    private GiamGiaType giamGiaType;

    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String danhMucId;

}

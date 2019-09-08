package com.ocha.boc.entity;

import com.ocha.boc.enums.GiamGiaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Document(collection = GiamGia.COLLECTION_NAME)
public class GiamGia {

    public static final String COLLECTION_NAME = "giamgia";

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String cuaHangId;

    private GiamGiaType giamGiaType;

    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String danhMucId;

}

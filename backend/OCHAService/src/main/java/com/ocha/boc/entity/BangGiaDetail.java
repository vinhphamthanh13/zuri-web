package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Document(collection = BangGiaDetail.COLLECTION_NAME)
public class BangGiaDetail extends AbstractEntity {

    public static final String COLLECTION_NAME = "banggia_detail";

    private String bangGiaId;

    private BigDecimal price;
}

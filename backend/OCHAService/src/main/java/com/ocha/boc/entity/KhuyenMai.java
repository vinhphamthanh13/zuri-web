package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = KhuyenMai.COLLECTION_NAME)
public class KhuyenMai extends AbstractEntity {

    public static final String COLLECTION_NAME = "khuyenmai";

    private String khuyenMaiId;

    private double rate;

    private String fromDate;

    private String toDate;

    private String cuaHangId;


}

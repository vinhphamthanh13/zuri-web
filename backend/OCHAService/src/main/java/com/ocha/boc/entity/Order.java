package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = Order.COLLECTION_NAME)
public class Order {
    public static final String COLLECTION_NAME = "order";

    private List<MatHangTieuThu> listMatHangTieuThu;

    private BigDecimal total;

    private String createDate;

    private String id;

    private BigDecimal sumOfKhuyenMaiPrice;

    private String cuaHangId;
}

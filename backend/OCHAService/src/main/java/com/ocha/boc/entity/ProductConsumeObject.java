package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductConsumeObject {

    private String productName;

    private String categoryId;

    private String priceName;

    private int quantity;

    private BigDecimal unitPrice;

}

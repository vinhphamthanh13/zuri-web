package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class DiscountReport {

    private String name;

    private int totalQuantity;

    private BigDecimal totalDiscount;

    private List<BaoCaoGiamGiaDetail> listDiscountInvoice;

}

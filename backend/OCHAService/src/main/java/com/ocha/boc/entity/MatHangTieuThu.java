package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class MatHangTieuThu {

    private MatHang matHang;
    /**
     * Price: Đơn giá của mặt hàng được tiêu thụ
     */
    private BigDecimal unitPrice;

    private int amountOfConsumption;

    private BigDecimal discountPrice = BigDecimal.ZERO;

}

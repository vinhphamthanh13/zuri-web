package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class MatHangBanChay {

    private int amountOfConsumption;

    private BigDecimal unitPrice;

    private BigDecimal costPrice;

    private MatHang matHang;
}

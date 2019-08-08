package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class MatHangBanChay {

    private String name;

    private int quantity;

    private BigDecimal totalPrice;
}

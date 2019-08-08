package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class DanhMucBanChay {

    private String danhMucName;

    private int totalQuantity;

    private BigDecimal totalPrice;

    private List<MatHangBanChay> listMatHangBanChay;
}

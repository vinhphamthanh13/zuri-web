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

    private BangGia bangGia;

    private int quantity;

    private BigDecimal total;
}

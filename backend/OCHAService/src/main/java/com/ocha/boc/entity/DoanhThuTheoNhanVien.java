package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class DoanhThuTheoNhanVien {

    private String employeeName;

    private int totalOrderSuccess;

    private BigDecimal totalPrice;

    private List<OrdersWerePaidInformation> listOrdersWerePaidInfor;
}

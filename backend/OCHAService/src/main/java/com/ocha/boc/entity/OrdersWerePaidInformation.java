package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OrdersWerePaidInformation {

    private String receiptCode;

    private String time;

    private BigDecimal totalPrice;
}

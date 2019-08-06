package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class DoanhThuTongQuanResponse extends AbstractResponse {

    private String cuaHangId;

    private BigDecimal total;

    private int numberOfTransactions;

    private BigDecimal averageOfTransactionFee;

    private BigDecimal discount;

    private BigDecimal refunds;

    private BigDecimal serviceFee;

    private BigDecimal tax ;
}

package com.ocha.boc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ocha.boc.enums.RevenuePercentageStatusType;
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

    @JsonProperty(value = "percent")
    private String revenuePercentage;

    private RevenuePercentageStatusType status;

    private BigDecimal totalPrice;
}

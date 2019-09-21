package com.ocha.boc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ocha.boc.enums.PercentageRevenueStatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class HotDealsProduct {

    private String name;

    private int quantity;

    @JsonProperty(value = "percent")
    private String revenuePercentage;

    private PercentageRevenueStatusType status;

    private BigDecimal totalPrice;
}

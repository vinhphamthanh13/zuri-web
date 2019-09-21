package com.ocha.boc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ocha.boc.enums.PercentageRevenueStatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class HotDealsCategory {

    private String categoryName;

    @JsonProperty(value = "percent")
    private String revenuePercentage;

    private PercentageRevenueStatusType status;

    private int totalQuantity;

    private BigDecimal totalPrice;

    private List<HotDealsProduct> hotDealsProducts;
}

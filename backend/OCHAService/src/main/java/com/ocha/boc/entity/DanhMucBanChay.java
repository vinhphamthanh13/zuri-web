package com.ocha.boc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ocha.boc.enums.RevenuePercentageStatusType;
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

    @JsonProperty(value = "percent")
    private String revenuePercentage;

    private RevenuePercentageStatusType status;

    private int totalQuantity;

    private BigDecimal totalPrice;

    private List<MatHangBanChay> listMatHangBanChay;
}

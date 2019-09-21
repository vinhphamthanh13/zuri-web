package com.ocha.boc.request;

import com.ocha.boc.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class DiscountUpdateRequest {

    @NotNull
    private String discountId;

    @NotNull
    private String restaurantId;

    private DiscountType discountType;

    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String categoryId;
}

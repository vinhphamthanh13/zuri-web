package com.ocha.boc.request;

import com.ocha.boc.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class DiscountRequest implements Serializable {

    @NotNull
    @NotEmpty
    private String restaurantId;

    @NotNull
    private DiscountType discountType;

    @NotNull
    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String categoryId;
}

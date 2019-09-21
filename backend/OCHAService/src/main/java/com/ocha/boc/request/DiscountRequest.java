package com.ocha.boc.request;

import com.ocha.boc.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class DiscountRequest implements Serializable {

    @NonNull
    private String restaurantId;

    @NonNull
    private DiscountType discountType;

    @NonNull
    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String  categoryId;
}

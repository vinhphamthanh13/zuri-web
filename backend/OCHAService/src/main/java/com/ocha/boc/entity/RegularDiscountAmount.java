package com.ocha.boc.entity;

import com.ocha.boc.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class RegularDiscountAmount extends RegularDiscount implements Serializable {

    private BigDecimal discountAmount;

    public RegularDiscountAmount(String name, String restaurantId, BigDecimal discountAmount) {
        super();
        this.setName(name);
        this.setRestaurantId(restaurantId);
        this.setCreatedDate(DateUtils.getCurrentDateAndTime());
        this.discountAmount = discountAmount;
    }
}

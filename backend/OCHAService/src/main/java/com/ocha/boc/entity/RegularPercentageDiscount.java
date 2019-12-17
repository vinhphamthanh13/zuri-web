package com.ocha.boc.entity;

import com.ocha.boc.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class RegularPercentageDiscount extends RegularDiscount implements Serializable {

    private BigDecimal percentage;

    public RegularPercentageDiscount(String name, String restaurantId, BigDecimal percentage) {
        super();
        this.setName(name);
        this.setRestaurantId(restaurantId);
        this.setCreatedDate(DateUtils.getCurrentDateAndTime());
        this.percentage = percentage;
    }
}

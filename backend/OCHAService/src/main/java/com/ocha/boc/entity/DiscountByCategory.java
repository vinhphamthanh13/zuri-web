package com.ocha.boc.entity;

import com.ocha.boc.enums.DiscountType;
import com.ocha.boc.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class DiscountByCategory extends Discount implements Serializable {

    private BigDecimal percentage;

    private String categoryId;

    private DiscountType discountType = DiscountType.GIẢM_GIÁ_THEO_DANH_MỤC;

    public DiscountByCategory(String categoryId, String name, String restaurantId, BigDecimal percentage) {
        super();
        this.setName(name);
        this.setRestaurantId(restaurantId);
        this.setCreatedDate(DateUtils.getCurrentDateAndTime());
        this.categoryId = categoryId;
        this.percentage = percentage;
    }
}

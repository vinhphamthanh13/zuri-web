package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.Discount;
import com.ocha.boc.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class DiscountDTO extends AbstractEntity {

    private String restaurantId;

    private DiscountType discountType;

    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String categoryId;

    public DiscountDTO(Discount discount) {
        this.id = discount.getId();
        this.restaurantId = discount.getRestaurantId();
        this.discountType = discount.getDiscountType();
        this.name = discount.getName();
        this.percentage = discount.getPercentage();
        this.discountAmount = discount.getDiscountAmount();
        this.categoryId = discount.getCategoryId();
        this.createdDate = discount.getCreatedDate();
        this.lastModifiedDate = discount.getLastModifiedDate();
    }
}

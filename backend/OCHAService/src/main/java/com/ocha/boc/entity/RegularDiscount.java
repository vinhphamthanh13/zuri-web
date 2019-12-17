package com.ocha.boc.entity;

import com.ocha.boc.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RegularDiscount extends Discount implements Serializable {

    private DiscountType discountType = DiscountType.GIẢM_GIÁ_THÔNG_THƯỜNG;


}

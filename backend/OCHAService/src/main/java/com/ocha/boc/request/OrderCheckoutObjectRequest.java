package com.ocha.boc.request;

import com.ocha.boc.entity.ProductConsumeObject;
import com.ocha.boc.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderCheckoutObjectRequest implements Serializable {

    @NotNull
    private String orderId;

    @NotNull
    private String restaurantId;

    @NotNull
    private List<ProductConsumeObject> listProductConsumeObject;

    @NotNull
    private BigDecimal cash;

    private BigDecimal tips;

    private DiscountType discountType;

    private String discountName;

    private BigDecimal percentageDiscount;

    private BigDecimal giamGiaDiscountAmount;

    private String categoryIsDiscountedId;
}

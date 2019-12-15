package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.enums.DiscountType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@Document(collection = Discount.COLLECTION_NAME)
public class Discount extends AbstractEntity implements Serializable {

    public static final String COLLECTION_NAME = "discount";

    @NotNull
    private String restaurantId;

    @NotNull
    private DiscountType discountType;

    @NotNull
    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String categoryId;

}

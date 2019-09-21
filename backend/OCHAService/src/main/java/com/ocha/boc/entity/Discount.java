package com.ocha.boc.entity;

import com.ocha.boc.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Document(collection = Discount.COLLECTION_NAME)
public class Discount {

    public static final String COLLECTION_NAME = "discount"; //giam gia

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

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

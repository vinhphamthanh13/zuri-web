package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Document(collection = Discount.COLLECTION_NAME)
public abstract class Discount extends AbstractEntity implements Serializable {

    public static final String COLLECTION_NAME = "discount";

    @NotNull
    private String name;

    @NotNull
    private String restaurantId;


}

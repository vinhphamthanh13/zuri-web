package com.ocha.boc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = Product.COLLECTION_NAME)
public class Product implements Serializable {

    public static final String COLLECTION_NAME = "product"; //mat hang

    private static Product EMPTY = new Product();

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String restaurantId;

    private String name;

    private String categoryId;

    @JsonProperty("priceList")
    private List<Price> prices = new ArrayList<Price>();

    public boolean checkObjectEmptyData()
    {
        return this.equals(EMPTY);
    }

}

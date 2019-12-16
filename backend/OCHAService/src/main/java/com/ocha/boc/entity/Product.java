package com.ocha.boc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@Document(collection = Product.COLLECTION_NAME)
public class Product implements Serializable {

    public static final String COLLECTION_NAME = "product"; //mat hang


    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String restaurantId;

    private String name;

    private String categoryId;

    @JsonProperty("priceList")
    private List<Price> prices = new ArrayList<Price>();



}

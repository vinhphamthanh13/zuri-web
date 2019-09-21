package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.Price;
import com.ocha.boc.entity.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductDTO extends AbstractEntity {

    private String restaurantId;

    private String name;

    private String categoryId;


    private List<Price> prices = new ArrayList<Price>();

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.createdDate = product.getCreatedDate();
        this.categoryId = product.getCategoryId();
        this.lastModifiedDate = product.getLastModifiedDate();
        this.restaurantId = product.getRestaurantId();
        this.prices = product.getPrices();
    }
}

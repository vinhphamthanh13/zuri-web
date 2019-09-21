package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDTO extends AbstractEntity {


    private String abbreviations;

    private String name;

    private String categoryId;

    private String restaurantId;

    public CategoryDTO(Category category) {
        this.abbreviations = category.getAbbreviations();
        this.name = category.getName();
        this.id = category.getId();
        this.categoryId = category.getCategoryId();
        this.createdDate = category.getCreatedDate();
        this.lastModifiedDate = category.getLastModifiedDate();
        this.restaurantId = category.getRestaurantId();
    }
}

package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Document(collection = Category.COLLECTION_NAME)
public class Category implements Serializable {

    public static final String COLLECTION_NAME = "category"; // danh muc

    private static Category EMPTY = new Category();

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String abbreviations;

    private String name;

    private String categoryId;

    private String restaurantId;

    public boolean checkObjectEmptyData() {
        return this.equals(EMPTY);
    }
}

package com.ocha.boc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@Document(collection = Category.COLLECTION_NAME)
public class Category implements Serializable {

    public static final String COLLECTION_NAME = "category"; // danh muc



    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String abbreviations;

    private String name;

    private String categoryId;

    private String restaurantId;


}

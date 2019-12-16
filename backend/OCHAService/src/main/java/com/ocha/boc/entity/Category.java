package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@Document(collection = Category.COLLECTION_NAME)
public class Category extends AbstractEntity implements Serializable {

    public static final String COLLECTION_NAME = "category"; // danh muc

    private String abbreviations;

    private String name;

    private String categoryId;

    private String restaurantId;


}

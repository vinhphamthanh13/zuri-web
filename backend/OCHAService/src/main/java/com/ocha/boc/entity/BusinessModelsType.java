package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = BusinessModelsType.COLLECTION_NAME)
public class BusinessModelsType {

    public static final String COLLECTION_NAME = "businessModels";

    private String name;

    @Id
    private String id;
}

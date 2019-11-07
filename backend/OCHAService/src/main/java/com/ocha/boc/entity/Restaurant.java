package com.ocha.boc.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = Restaurant.COLLECTION_NAME)
public class Restaurant {

    public static final String COLLECTION_NAME = "restaurant";

    private String id;

    private String businessModelsType; //mo hinh kinh doanh

    private String businessItemsType; //danh muc mat hang type

    private String restaurantName;

    private String phone;

    private String address;

    private String managerName;

    private String managerPhone;

    private String managerEmail;

    private String createdDate;

    private String lastModifiedDate;

    private String userId;


}

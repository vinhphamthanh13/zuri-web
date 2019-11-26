package com.ocha.boc.dto;

import com.ocha.boc.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestaurantDTO {
    private String id;

    private String businessModelsType; //mo hinh kinh doanh

    private String businessItemsType; //danh muc mat hang type

    private String restaurantName;

    private String restaurantPhone;

    private String address;

    private String managerName;

    private String managerPhone;

    private String managerEmail;

    private String createdDate;

    private String lastModifiedDate;

    public RestaurantDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.businessModelsType = restaurant.getBusinessModelsType();
        this.businessItemsType = restaurant.getBusinessItemsType();
        this.restaurantName = restaurant.getRestaurantName();
        this.restaurantPhone = restaurant.getRestaurantPhone();
        this.address = restaurant.getAddress();
        this.managerName = restaurant.getManagerName();
        this.managerPhone = restaurant.getManagerPhone();
        this.managerEmail = restaurant.getManagerEmail();
        this.createdDate = restaurant.getCreatedDate();
        this.lastModifiedDate = restaurant.getLastModifiedDate();
    }
}

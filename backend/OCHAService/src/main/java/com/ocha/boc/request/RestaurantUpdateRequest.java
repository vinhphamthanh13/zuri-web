package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class RestaurantUpdateRequest implements Serializable {

    @NotNull
    private String restaurantId;


    private String restaurantPhone;

    private String businessModelsType; //mo hinh kinh doanh

    private String businessItemsType; //danh muc mat hang type

    private String address;

    private String managerEmail;

    @NotNull
    private String managerPhone;

    private String managerName;
}

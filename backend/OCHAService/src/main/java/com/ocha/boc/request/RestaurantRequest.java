package com.ocha.boc.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class RestaurantRequest implements Serializable {

    @NotNull
    private String businessModelsType; //mo hinh kinh doanh

    @NotNull
    private String businessItemsType; //danh muc mat hang type

    @NotNull
    private String restaurantName;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    private String managerName;

    private String managerPhone;

    private String managerEmail;
}

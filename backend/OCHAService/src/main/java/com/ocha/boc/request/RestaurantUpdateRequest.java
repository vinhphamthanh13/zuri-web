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

    @NotNull
    private String phone;

    private String businessModelsType; //mo hinh kinh doanh

    private String businessItemsType; //danh muc mat hang type

    private String address;

    private String managerEmail;

    private String managerPhone;

    private String managerName;
}

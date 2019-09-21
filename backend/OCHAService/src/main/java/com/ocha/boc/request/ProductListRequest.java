package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductListRequest extends PageRequest {

    private String restaurantId;

    private String name;
}

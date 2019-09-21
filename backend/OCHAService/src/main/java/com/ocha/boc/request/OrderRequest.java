package com.ocha.boc.request;

import com.ocha.boc.entity.ProductConsumeObject;
import com.ocha.boc.enums.OrderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderRequest implements Serializable {

    @NotNull
    private String restaurantId;

    @NotNull
    private String waiterName;

    @NotNull
    private OrderType orderType;

    @NotNull
    private List<ProductConsumeObject> listProductConsumeObject = new ArrayList<ProductConsumeObject>();

    private String orderLocation;
}

package com.ocha.boc.request;

import com.ocha.boc.entity.ProductConsumeObject;
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
public class OrderUpdateRequest implements Serializable {

    @NotNull
    private String orderId;

    @NotNull
    private String restaurantId;

    @NotNull
    private List<ProductConsumeObject> listProductConsumeObject = new ArrayList<ProductConsumeObject>();

    private String orderLocation;
}

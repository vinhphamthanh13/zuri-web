package com.ocha.boc.request;

import com.ocha.boc.entity.ProductConsumeObject;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OrderRejectObjectRequest implements Serializable {

    @NotNull
    private String orderId;

    @NotNull
    private String restaurantId;

    @NotNull
    private List<ProductConsumeObject> listProductConsumeObject;
}

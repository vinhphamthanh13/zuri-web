package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.entity.HotDealsProduct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class HotDealsProductsResponse extends AbstractResponse {

    private String restaurantId;

    List<HotDealsProduct> hotDealsProductList;
}

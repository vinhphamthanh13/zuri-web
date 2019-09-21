package com.ocha.boc.request;

import com.ocha.boc.entity.Price;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductUpdateRequest implements Serializable {

    @NotNull
    private String id;

    @NotNull
    private String restaurantId;

    @NotNull
    private String name;

    private String categoryId;

    private List<Price> prices = new ArrayList<Price>();
}

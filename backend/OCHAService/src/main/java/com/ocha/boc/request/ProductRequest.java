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
public class ProductRequest implements Serializable {

    @NotNull
    private String restaurantId;

    @NotNull
    private String name;

    @NotNull
    private String categoryId;

    @NotNull
    private List<Price> prices = new ArrayList<Price>();

}

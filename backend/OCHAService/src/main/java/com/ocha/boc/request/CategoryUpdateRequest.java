package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class CategoryUpdateRequest implements Serializable {

    private String abbreviations;

    private String name;

    private String categoryId;

    @NotNull
    private String restaurantId;
}

package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class CategoryRequest implements Serializable {

    private String abbreviations;

    @NotNull
    private String name;

    @NotNull
    private String restaurantId;
}

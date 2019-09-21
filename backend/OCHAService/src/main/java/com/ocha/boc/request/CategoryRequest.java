package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CategoryRequest implements Serializable {

    private String abbreviations;

    @NonNull
    private String name;

    @NonNull
    private String restaurantId;
}

package com.ocha.boc.request;


import com.ocha.boc.enums.Region;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class GiayInRequest implements Serializable {

    private String title;

    private String description;

    private BigDecimal price;

    private Region region;
}

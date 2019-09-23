package com.ocha.boc.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CopyPrintersRequest implements Serializable {

    private String title;

    private String description;

    private BigDecimal price;

    private String region;
}

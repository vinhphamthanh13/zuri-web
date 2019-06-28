package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class BangGiaDetailRequest implements Serializable {

    private String bangGiaId;

    private BigDecimal price;
}

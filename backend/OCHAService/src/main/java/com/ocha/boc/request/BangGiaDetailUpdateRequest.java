package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class BangGiaDetailUpdateRequest implements Serializable {

    private String id;

    private String bangGiaId;

    private BigDecimal price;
}

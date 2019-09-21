package com.ocha.boc.entity;

import com.ocha.boc.enums.EPriceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class PriceType implements Serializable {


    private EPriceType name;
    private BigDecimal price;
}

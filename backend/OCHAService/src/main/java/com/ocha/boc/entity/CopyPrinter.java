package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.enums.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class CopyPrinter extends AbstractEntity {

    public static final String COLLECTION_NAME = "copyprinter";

    private String title;

    private String description;

    private BigDecimal price;

    private Region region;

}

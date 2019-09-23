package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@Document(collection = CopyPrinter.COLLECTION_NAME)
public class CopyPrinter extends AbstractEntity {

    public static final String COLLECTION_NAME = "copyprinter";

    private String title;

    private String description;

    private BigDecimal price;

    private String region;

}

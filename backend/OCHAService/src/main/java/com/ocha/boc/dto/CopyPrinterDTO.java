package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.CopyPrinter;
import com.ocha.boc.enums.Region;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CopyPrinterDTO extends AbstractEntity {

    private String title;

    private String description;

    private BigDecimal price;

    private Region region;

    public CopyPrinterDTO(CopyPrinter copyPrinter) {
        this.id = copyPrinter.getId();
        this.title = copyPrinter.getTitle();
        this.description = copyPrinter.getDescription();
        this.price = copyPrinter.getPrice();
        this.region = copyPrinter.getRegion();
        this.createdDate = copyPrinter.getCreatedDate();
        this.lastModifiedDate = copyPrinter.getLastModifiedDate();
    }
}

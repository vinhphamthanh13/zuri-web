package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.BangGia;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BangGiaDTO extends AbstractEntity {

    private int numberOfPrice;

    public BangGiaDTO(BangGia bangGia) {
        this.createdDate = bangGia.getCreatedDate();
        this.lastModifiedDate = bangGia.getLastModifiedDate();
        this.numberOfPrice = bangGia.getNumberOfPrice();
        this.id = bangGia.getId();
    }
}

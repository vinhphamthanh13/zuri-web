package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.BangGiaDetail;

import java.math.BigDecimal;

public class BangGiaDetailDTO extends AbstractEntity {


    private String bangGiaId;

    private BigDecimal price;

    public BangGiaDetailDTO(BangGiaDetail bangGiaDetail) {
        this.bangGiaId = bangGiaDetail.getBangGiaId();
        this.price = bangGiaDetail.getPrice();
    }
}

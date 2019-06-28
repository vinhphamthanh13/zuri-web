package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.BangGiaDetail;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BangGiaDetailDTO extends AbstractEntity {


    private String bangGiaId;

    private BigDecimal price;

    public BangGiaDetailDTO(BangGiaDetail bangGiaDetail) {
        this.bangGiaId = bangGiaDetail.getBangGiaId();
        this.price = bangGiaDetail.getPrice();
        this.createdDate = bangGiaDetail.getCreatedDate();
        this.id = bangGiaDetail.getId();
    }
}

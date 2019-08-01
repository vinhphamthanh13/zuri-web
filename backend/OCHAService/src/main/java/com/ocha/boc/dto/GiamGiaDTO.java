package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.GiamGia;
import com.ocha.boc.enums.GiamGiaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class GiamGiaDTO extends AbstractEntity {

    private String cuaHangId;

    private GiamGiaType giamGiaType;

    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String danhMucId;

    public GiamGiaDTO(GiamGia giamGia) {
        this.id = giamGia.getId();
        this.cuaHangId = giamGia.getCuaHangId();
        this.giamGiaType = giamGia.getGiamGiaType();
        this.name = giamGia.getName();
        this.percentage = giamGia.getPercentage();
        this.discountAmount = giamGia.getDiscountAmount();
        this.danhMucId = giamGia.getDanhMucId();
        this.createdDate = giamGia.getCreatedDate();
        this.lastModifiedDate = giamGia.getLastModifiedDate();
    }
}

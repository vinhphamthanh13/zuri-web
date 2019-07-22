package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.KhuyenMai;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KhuyenMaiDTO extends AbstractEntity {

    private String khuyenMaiId;

    private double rate;

    private String fromDate;

    private String toDate;

    private String cuaHangId;

    public KhuyenMaiDTO(KhuyenMai khuyenMai) {
        this.khuyenMaiId = khuyenMai.getKhuyenMaiId();
        this.rate = khuyenMai.getRate();
        this.fromDate = khuyenMai.getFromDate();
        this.toDate = khuyenMai.getToDate();
        this.id = khuyenMai.getId();
        this.createdDate = khuyenMai.getCreatedDate();
        this.lastModifiedDate = khuyenMai.getLastModifiedDate();
        this.cuaHangId = khuyenMai.getCuaHangId();
    }
}

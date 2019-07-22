package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.MatHang;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MathangDTO extends AbstractEntity {

    private String name;

    private String bangGiaId;

    private String danhMucId;

    private String khuyenMaiId;

    private String cuaHangId;

    public MathangDTO(MatHang matHang) {
        this.id = matHang.getId();
        this.name = matHang.getName();
        this.createdDate = matHang.getCreatedDate();
        this.bangGiaId = matHang.getBangGiaId();
        this.danhMucId = matHang.getDanhMucId();
        this.khuyenMaiId = matHang.getKhuyenMaiId();
        this.lastModifiedDate = matHang.getLastModifiedDate();
        this.cuaHangId = matHang.getCuaHangId();
    }
}

package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.DanhMucSanPham;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DanhMucSanPhamDTO extends AbstractEntity {

    private String name;

    public DanhMucSanPhamDTO(DanhMucSanPham danhMucSanPham) {
        this.name = danhMucSanPham.getName();
    }
}

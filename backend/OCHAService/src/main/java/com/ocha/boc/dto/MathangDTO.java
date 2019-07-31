package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.BangGia;
import com.ocha.boc.entity.MatHang;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MathangDTO extends AbstractEntity {

    private String cuaHangId;

    private String name;

    private String danhMucId;

    private List<BangGia> listBangGia = new ArrayList<BangGia>();

    private String khuyenMaiId;



    public MathangDTO(MatHang matHang) {
        this.id = matHang.getId();
        this.name = matHang.getName();
        this.createdDate = matHang.getCreatedDate();
        this.danhMucId = matHang.getDanhMucId();
        this.khuyenMaiId = matHang.getKhuyenMaiId();
        this.lastModifiedDate = matHang.getLastModifiedDate();
        this.cuaHangId = matHang.getCuaHangId();
        this.listBangGia = matHang.getListBangGia();
    }
}

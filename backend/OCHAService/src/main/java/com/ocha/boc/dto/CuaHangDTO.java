package com.ocha.boc.dto;

import com.ocha.boc.entity.CuaHang;
import com.ocha.boc.enums.MoHinhKinhDoanhType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CuaHangDTO {
    private String id;

    private MoHinhKinhDoanhType moHinhKinhDoanhType;

    private String cuaHangName;

    private String phone;

    private String address;

    private String managerName;

    private String managerPhone;

    private String managerEmail;

    public CuaHangDTO(CuaHang cuaHang) {
        this.id = cuaHang.getId();
        this.moHinhKinhDoanhType = cuaHang.getMoHinhKinhDoanhType();
        this.cuaHangName = cuaHang.getCuaHangName();
        this.phone = cuaHang.getPhone();
        this.address = cuaHang.getAddress();
        this.managerName = cuaHang.getManagerName();
        this.managerPhone = cuaHang.getManagerPhone();
        this.managerEmail = cuaHang.getManagerEmail();
    }
}

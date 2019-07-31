package com.ocha.boc.dto;

import com.ocha.boc.entity.NhanVien;
import com.ocha.boc.enums.NhanVienType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NhanVienDTO {

    private String cuaHangId;

    private String username;

    private String password;

    private String fullName;

    private NhanVienType nhanVienType;

    public NhanVienDTO(NhanVien nhanVien) {
        this.cuaHangId = nhanVien.getCuaHangId();
        this.username = nhanVien.getUsername();
        this.password = nhanVien.getPassword();
        this.fullName = nhanVien.getFullName();
        this.nhanVienType = nhanVien.getNhanVienType();
    }
}

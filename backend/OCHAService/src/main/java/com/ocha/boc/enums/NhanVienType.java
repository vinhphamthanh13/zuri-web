package com.ocha.boc.enums;

public enum NhanVienType {

    NHÂN_VIÊN("Nhân viên"),

    QUẢN_LÝ("Quản lý"),

    PHỤC_VỤ("Phục vụ");

    public final String label;

    private NhanVienType(String label){
        this.label = label;
    }
}

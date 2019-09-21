package com.ocha.boc.enums;

public enum EmployeeRole {

    NHÂN_VIÊN("Nhân viên"),

    QUẢN_LÝ("Quản lý"),

    PHỤC_VỤ("Phục vụ");

    public final String label;

    EmployeeRole(String label) {
        this.label = label;
    }
}

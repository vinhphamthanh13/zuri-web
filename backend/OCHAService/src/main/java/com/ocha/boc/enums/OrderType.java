package com.ocha.boc.enums;

public enum OrderType {

    MANG_ĐI("Mang đi"),

    DÙNG_TẠI_BÀN("Dùng tại bàn");

    public final String label;

    private OrderType(String label){
        this.label = label;
    }
}

package com.ocha.boc.enums;

public enum GiamGiaType {

    GIẢM_GIÁ_THÔNG_THƯỜNG("Giảm giá thông thường"),

    GIẢM_GIÁ_THEO_DANH_MỤC("Giảm giá theo danh mục");

    public final String label;

    private GiamGiaType(String label){
        this.label = label;
    }
}

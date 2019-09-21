package com.ocha.boc.enums;

public enum DiscountType {

    NONE("NONE"),

    GIẢM_GIÁ_THÔNG_THƯỜNG("Giảm giá thông thường"),

    GIẢM_GIÁ_THEO_DANH_MỤC("Giảm giá theo danh mục");

    public final String label;

    private DiscountType(String label){
        this.label = label;
    }
}

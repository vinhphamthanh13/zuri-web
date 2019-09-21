package com.ocha.boc.enums;

public enum EPriceType {

    GIÁ_THƯỜNG("Giá thường"),

    GIÁ_TÙY_CHỈNH("Giá tùy chỉnh");

    public final String label;

    private EPriceType(String label){
        this.label = label;
    }
}

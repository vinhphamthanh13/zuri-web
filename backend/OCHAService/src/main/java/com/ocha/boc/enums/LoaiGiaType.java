package com.ocha.boc.enums;

public enum LoaiGiaType {

    GIÁ_THƯỜNG("Giá thường"),

    GIÁ_TÙY_CHỈNH("Giá tùy chỉnh");

    public final String label;

    private LoaiGiaType(String label){
        this.label = label;
    }
}

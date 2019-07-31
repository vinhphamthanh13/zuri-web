package com.ocha.boc.enums;

public enum DanhMucMatHangType {

    MÓN_ĂN_THÁI("Món ăn Thái"),

    MÓN_ĂN_NHẬT("Món ăn Nhật"),

    MÓN_ĂN_TÂY("Món ăn Tây"),

    MÓN_ĂN_TRUNG_QUỐC("Món ăn Trung Quốc"),

    MÓN_ĂN_HÀN_QUỐC("Món ăn Hàn Quốc"),

    MỲ("Mỳ"),

    MÓN_TRÁNG_MIỆNG("Món tráng miệng"),

    THỨC_ĂN_NHẸ("Thức ăn nhẹ"),

    THỨC_ĂN_NHANH("Thức ăn nhanh"),

    BÁNH("Bánh"),

    QUÁN_CÀ_PHÊ("Quán cà phê"),

    KHÁC("Khác");

    public final String label;

    private DanhMucMatHangType(String label){
        this.label = label;
    }
}

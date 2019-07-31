package com.ocha.boc.enums;

public enum MoHinhKinhDoanhType {
    QUÁN_CAO_CẤP("Quán cao cấp"),

    QUÁN_BÌNH_DÂN("Quán bình dân"),

    QUÁN_ĂN_NHANH("Quán ăn nhanh"),

    QUÁN_ĂN_TỰ_CHỌN("Quán ăn tự chọn"),

    NHƯỢNG_QUYỀN("Nhượng quyền"),

    PHỤC_VỤ_NHANH_MANG_ĐI("Phục vụ nhanh mang đi"),

    KHÁC("Khác");

    public final String label;

    private MoHinhKinhDoanhType(String label){
        this.label = label;
    }
}

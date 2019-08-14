package com.ocha.boc.enums;

public enum Region {

    MIỀN_BẮC("Miền Bắc"),

    MIỀN_TRUNG("Miền Trung"),

    MIỀN_NAM("Miền Nam");

    public final String label;

    private Region(String label){
        this.label = label;
    }
}

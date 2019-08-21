package com.ocha.boc.enums;


public enum RevenuePercentageStatusType {

    INCREASE("INCREASE"),

    DECREASE("DECREASE"),

    INCREASE_INFINITY("INCREASE_INFINITY"),

    DECREASE_INFINITY("DECREASE_INFINITY"),

    NORMAL("NORMAL");

    public final String label;

    private RevenuePercentageStatusType(String label){
        this.label = label;
    }
}

package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class DanhMucRequest implements Serializable {

    private String abbreviations;

    private String name;

    private String cuaHangId;
}

package com.ocha.boc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BangGia implements Serializable {

    private String name;

    private LoaiGia loaiGia;
}

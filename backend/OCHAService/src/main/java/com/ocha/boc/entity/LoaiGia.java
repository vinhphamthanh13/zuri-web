package com.ocha.boc.entity;

import com.ocha.boc.enums.LoaiGiaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class LoaiGia implements Serializable {

    private LoaiGiaType loaiGiaType;
    private BigDecimal price;
}

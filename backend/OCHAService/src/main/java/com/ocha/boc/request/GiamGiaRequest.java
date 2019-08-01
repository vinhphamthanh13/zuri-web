package com.ocha.boc.request;

import com.ocha.boc.entity.DanhMuc;
import com.ocha.boc.enums.GiamGiaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class GiamGiaRequest implements Serializable {

    @NonNull
    private String cuaHangId;

    @NonNull
    private GiamGiaType giamGiaType;

    @NonNull
    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String  danhMucId;
}

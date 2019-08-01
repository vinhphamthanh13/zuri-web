package com.ocha.boc.request;

import com.ocha.boc.enums.GiamGiaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class GiamGiaUpdateRequest {

    @NotNull
    private String giamGiaId;

    @NotNull
    private String cuaHangId;

    private GiamGiaType giamGiaType;

    private String name;

    private BigDecimal percentage;

    private BigDecimal discountAmount;

    private String danhMucId;
}
